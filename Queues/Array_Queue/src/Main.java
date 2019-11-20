public class Main {

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i< 5; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue);
        System.out.println(queue.dequeue());
        System.out.println(queue.getFront());
        queue.enqueue(0);
        System.out.println(queue);
    }
}
