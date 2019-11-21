public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(1);
        linkedList.addLast(2);
        linkedList.add(1,3);
        System.out.println(linkedList.getSize());
        System.out.println(linkedList.getLast());
        System.out.println(linkedList.contains(2));
        linkedList.addLast(4);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList);

        linkedList.remove(1);
        linkedList.removeFirst();
        linkedList.removeLast();
        System.out.println(linkedList);

        linkedList.addLast(3);
        linkedList.addFirst(1);
        System.out.println(linkedList);
    }
}
