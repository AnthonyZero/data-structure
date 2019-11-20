import java.util.Random;

//普通队列 和循环队列的 性能比较
public class PerformanceComparison {

    // 测试使用q运行opCount个enqueueu和dequeue操作所需要的时间，单位：秒
    private static double testQueue(AbstractQueue<Integer> q, int opCount){

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0 ; i < opCount ; i ++)
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        for(int i = 0 ; i < opCount ; i ++)
            q.dequeue();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0; //返回秒
    }

    public static void main(String[] args) {

        int opCount = 100000;

        Queue<Integer> arrayQueue = new Queue<>();
        double time1 = testQueue(arrayQueue, opCount);
        System.out.println("Queue, time: " + time1 + " s");

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double time2 = testQueue(loopQueue, opCount);
        System.out.println("LoopQueue, time: " + time2 + " s");
    }
}
