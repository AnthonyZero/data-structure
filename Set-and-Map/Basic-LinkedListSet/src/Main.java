public class Main {

    //用我们实现的链表 来实现Set
    public static void main(String[] args) {
        LinkedListSet<Integer> set = new LinkedListSet<>();
        for (int i = 0; i <= 10; i++) {
            set.add(i);
        }
        set.add(2);
        set.add(3);
        System.out.println(set.contains(2));
        System.out.println(set.getSize());
        set.remove(2);
        System.out.println(set.contains(2));
    }
}
