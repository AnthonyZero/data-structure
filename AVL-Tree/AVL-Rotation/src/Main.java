import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        AVLTree<Integer,Integer> avlTree = new AVLTree<>();
        for (int i = 0; i < 100000; i ++) {
            avlTree.add(random.nextInt(10000), 0);
        }
        System.out.println(avlTree.isBST());
        System.out.println(avlTree.isBalanced());
    }
}
