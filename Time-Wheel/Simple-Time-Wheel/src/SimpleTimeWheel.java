import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单时间轮：一层
 * 环形的数组：当我们需要取出延时消息时，只需要每秒往下移动这个指针，然后取出该位置的所有任务即可
 * 当然取出任务之前还得判断圈数是否为 0 ，不为 0 时说明该任务还得再轮几圈，同时需要将圈数 -1 。
 *
 * 这样就可避免轮询所有的任务，不过如果时间轮的槽比较少，导致某一个槽上的任务非常多那效率也比较低，这就和 HashMap 的 hash 冲突是一样的
 */
public class SimpleTimeWheel {
    //默认一圈多少容量 一圈64秒
    private static final int STATIC_WHEEL_SIZE = 64;
    //数据 一维数组 每个index上是个任务集合
    private Object[] wheel;
    //时间轮数组长度
    private int wheelSize;

    //执行任务的线程池
    private ExecutorService executorService;
    //任务数量
    private volatile int size = 0;

    /***
     * 任务暂停的标志
     */
    private volatile boolean stop = false;

    /**
     * 任务开始的标志
     */
    private volatile AtomicBoolean start = new AtomicBoolean(false);


    private AtomicInteger tick = new AtomicInteger();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private AtomicInteger taskId = new AtomicInteger();
    //任务ID -> 任务 用于快速找到任务信息 取消任务执行
    private Map<Integer, Task> taskMap = new ConcurrentHashMap<>(16);


    public SimpleTimeWheel(ExecutorService executorService) {
        this.executorService = executorService;
        this.wheelSize = STATIC_WHEEL_SIZE;
        this.wheel = new Object[wheelSize];
    }


    //根据线程池和数组长度初始化
    public SimpleTimeWheel(ExecutorService executorService, int wheelSize) {
        this(executorService);

        if (!powerOf2(wheelSize)) {
            throw new RuntimeException("wheelSize=[" + wheelSize + "] 必须是2的幂");
        }
        this.wheelSize = wheelSize;
        this.wheel = new Object[wheelSize];
    }

    /**
     * 添加延时任务
     * @param task
     * @return 生成的任务ID
     */
    public int addTask(Task task) {
        int key = task.getKey(); //延时时间
        int id;
        try {
            lock.lock();
            int index = mod(key, wheelSize); //取模（延时任务时间 模 圈的大小） 获取时间轮上所在位置
            int cycleNum = cycleNum(key, wheelSize); //计算任务的时间 应该有几圈
            task.setIndex(index);
            task.setCycleNum(cycleNum);

            Set<Task> delayTasks = get(index); //获取此位置上的延时任务集合
            if (delayTasks != null) {
                delayTasks.add(task);
            } else {
                Set<Task> sets = new HashSet<>();
                sets.add(task);
                wheel[index] = sets; //初始化 此位置
            }
            id = taskId.incrementAndGet();
            task.setTaskId(id);
            taskMap.put(id, task);
            size++;
        } finally {
            lock.unlock();
        }

        start();

        return id;
    }


    /**
     * 根据时间（s） 取出来应该执行的任务集合
     * @param key 时间（秒）不能超过数组大小
     * @return
     */
    private Set<Task> remove(int key) {
        Set<Task> tempTask = new HashSet<>();
        Set<Task> result = new HashSet<>();

        Set<Task> tasks = (Set<Task>) wheel[key];
        if (tasks == null) {
            return result;
        }

        //在index 的任务集合中 有些任务的圈数 可能不是相同的
        for (Task task : tasks) {
            if (task.getCycleNum() == 0) {
                //只有任务的圈数为0的 时候才能被取出来 执行
                result.add(task);
                // remove task, and free the memory.
                taskMap.remove(task.getTaskId());

                size2Notify(); //size-- 任务数量为0的时候notify
            } else {
                // 减圈数 并更新
                task.setCycleNum(task.getCycleNum() - 1);
                tempTask.add(task);
            }
        }

        //更新原数据
        wheel[key] = tempTask;

        return result;
    }


    /**
     * 取消任务执行
     * @param id 任务ID
     * @return
     */
    public boolean cancel(int id) {

        boolean flag = false;
        Set<Task> tempTask = new HashSet<>();

        try {
            lock.lock();
            Task task = taskMap.get(id);
            if (task == null) { //如果任务已经执行 说明获取null值
                return false;
            }
            //获取index上的任务集合
            Set<Task> tasks = get(task.getIndex());
            for (Task tk : tasks) {
                if (tk.getKey() == task.getKey() && tk.getCycleNum() == task.getCycleNum()) { //对比time 和 圈数
                    size--;
                    flag = true;
                    taskMap.remove(id); //取消成功
                } else {
                    tempTask.add(tk);
                }

            }
            //更新index原数据
            wheel[task.getIndex()] = tempTask;
        } finally {
            lock.unlock();
        }

        return flag;
    }

    /**
     * 开启一个后台线程消费时间轮的任务
     * start() 在并发执行的时候只能执行一次，利用了 CAS 来保证同时只有一个线程可以执行成功
     */
    public void start() {
        //如果true已经开始了 直接返回
        if (!start.get()) {

            //并发问题 只启动一个后台线程不断remove消费任务 丢到线程池
            if (start.compareAndSet(start.get(), true)) {
                System.out.println("Delay task is starting");
                Thread job = new Thread(new TriggerJob());
                job.setName("consumer timeWheel thread");
                job.start();
                start.set(true);
            }

        }
    }

    /**
     * force为true 会强制关闭消费线程并丢弃所有待执行的任务
     * force为false 会等待所有的任务去完成 在关闭之前
     * @param force
     */
    public void stop(boolean force) {
        if (force) {
            //直接更新停止标志，同时关闭线程池即可
            System.out.println("Delay task is forced stop");
            stop = true;
            executorService.shutdownNow();
        } else {
            //阻塞主线程，直到任务执行完毕后被唤醒。
            System.out.println("Delay task is stopping");
            if (taskSize() > 0) {
                try {
                    lock.lock();
                    condition.await(); //等待所有任务消费完
                    stop = true;
                } catch (InterruptedException e) {
                    System.err.println(e);
                } finally {
                    lock.unlock();
                }
            }
            executorService.shutdown();
        }
    }


    public int taskSize() {
        return size;
    }
    public int taskMapSize(){
        return taskMap.size();
    }


    //或者index 上的任务集合
    private Set<Task> get(int index) {
        return (Set<Task>) wheel[index];
    }
    //put
    private void put(int key, Set<Task> tasks) {
        int index = mod(key, wheelSize);
        wheel[index] = tasks;
    }

    private void size2Notify() {
        try {
            lock.lock();
            size--;
            if (size == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    //目标值是否是2的幂
    private boolean powerOf2(int target) {
        if (target < 0) {
            return false;
        }
        // 1000  （8）
        // 0111  （7）
        int value = target & (target - 1);
        if (value != 0) {
            return false;
        }
        return true;
    }

    //位运算替代了取模
    private int mod(int target, int mod) {
        // equals target % mod
        target = target + tick.get();
        return target & (mod - 1);
    }
    //计算该任务所处的圈数，考虑到效率问题，使用位运算替代了除法。
    private int cycleNum(int target, int mod) {
        //equals target/mod
        return target >> Integer.bitCount(mod - 1);
    }



    /**
     * 抽象类 任务 用于业务继承
     */
    public abstract static class Task extends Thread {

        private int index;

        private int cycleNum;

        private int key;

        /**
         * 任务ID 唯一
         */
        private int taskId ;

        @Override
        public void run() {
        }

        public int getKey() {
            return key;
        }

        /**
         *
         * key 延时任务的延时时间 秒为单位
         */
        public void setKey(int key) {
            this.key = key;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        private void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }

        public int getIndex() {
            return index;
        }

        private void setIndex(int index) {
            this.index = index;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }

    //核心后台线程 不断执行remove获取任务列表 丢到线程池执行
    private class TriggerJob implements Runnable {
        @Override
        public void run() {
            int index = 0;
            while (!stop) {
                try {
                    //执行任务
                    Set<Task> tasks = remove(index);
                    for (Task task : tasks) {
                        executorService.submit(task);
                    }
                    //每次拨动一个指针+1 index 不能超过 时间轮的数组大小
                    if (++index > wheelSize - 1) {
                        index = 0; //归0
                    }

                    //Total tick number of records
                    tick.incrementAndGet();
                    TimeUnit.SECONDS.sleep(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("延时任务已经停止");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        SimpleTimeWheel timeWheel = new SimpleTimeWheel(executorService, 128);
        for(int i = 0; i < 1000; i++) {
            String msg = String.format("delay time %d", i);
            DelayTaskJob task = new DelayTaskJob(i, msg);
            timeWheel.addTask(task);
        }
        //timeWheel.start();
        Thread.sleep(10000);
        timeWheel.stop(false);
    }

    private static class DelayTaskJob extends SimpleTimeWheel.Task {
        private String msg;

        public DelayTaskJob(int time, String msg) {
            this.msg = msg;
            setKey(time);
        }
        @Override
        public void run() {
            System.out.println(msg);
        }
    }
}
