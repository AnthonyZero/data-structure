
/**
 * 循环队列  两个指针 一个指向队头元素 一个指向下一个（下一个进队）元素
 * 底层还是数组 两个指针移动 循环数组
 * @param <E>
 */
public class LoopQueue<E> implements AbstractQueue<E> {

    private E[] data;
    private int front;
    private int tail;
    private int size;

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];//预留一个位置 tail
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail; //数组不为空的时候 要么tail < front 要么tail > front;
    }

    @Override
    public void enqueue(E e) {
        if((tail + 1) % data.length == front) {
            //入队前检测 数组是否要满了 满了就扩容
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length; //为什么不直接+1就行了 应为tail可能跑到front前面去 0开始
        size ++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()) {
            throw new IllegalArgumentException("出队异常，队列为空");
        }
        E ret = data[front];
        data[front] = null; //gc
        front = (front + 1) % data.length;
        size --;
        //缩容
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if(isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }
        return data[front];
    }

    //扩容 把front(包括front)后面的元素依次移动到 从数组下标0开始排列。 tail前面的元素依次排在后面
    private void resize(int newCapacity) {
        E[] newData = (E[])new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + front) % data.length];
        }
        data = newData;
        front = 0; //front重新指向0
        tail = size; //tail指向最后（下一次元素入队的位置）
    }

    @Override
    public String toString(){

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for(int i = front ; i != tail ; i = (i + 1) % data.length){
            res.append(data[i]);
            if((i + 1) % data.length != tail)  //(i + 1) % data.length 就是下一个元素位置
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }
}
