public class Main {

    //用我们自己的二叉搜索树 实现集合Set
    public static void main(String[] args) {
        BSTSet<Integer> set = new BSTSet<>();
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
