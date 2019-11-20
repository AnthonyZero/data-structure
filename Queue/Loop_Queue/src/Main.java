public class Main {

    // 普通队列在出队是O(n)的复杂度，因为队首出队之后 要移动后面的元素到前面来。
    // 而循环队列 优化之后出队是O（1）的复杂度 （均摊复杂度）
    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>();
        for (int i = 0; i< 10; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue);
        queue.enqueue(11);
        System.out.println(queue);
        System.out.println(queue.dequeue());
        System.out.println(queue.getFront());
        queue.enqueue(12);
        queue.enqueue(13);
        queue.enqueue(14);
        System.out.println(queue);
    }
}
