public class Main {

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        System.out.println(uf.isConnected(0,1));
        uf.unionElements(0, 2);
        uf.unionElements(1, 5);
        uf.unionElements(2, 4);
        uf.unionElements(1,6);
        uf.unionElements(3, 4);
        System.out.println(uf.isConnected(0, 1));
        System.out.println(uf.isConnected(1, 6));
        System.out.println(uf.isConnected(0, 6));
        uf.unionElements(0, 1);
        System.out.println(uf.isConnected(0, 6));
    }
}
