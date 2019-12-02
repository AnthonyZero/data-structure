public class Main {

    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        tree.add(1);
        tree.add(0);
        tree.add(4);
        tree.add(3);
        System.out.println(tree.contains(4));
        System.out.println(tree.contains(-1));
    }
}
