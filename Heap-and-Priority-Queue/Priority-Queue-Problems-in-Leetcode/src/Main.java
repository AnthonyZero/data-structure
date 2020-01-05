public class Main {

    public static void main(String[] args) {

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(1);
        queue.enqueue(5);
        queue.enqueue(0);
        System.out.println(queue.dequeue());
        System.out.println(queue.getFront());
    }
}
