public class UnionFind implements UF {

    private int[] parent; //parent[i]表示第一个元素所指向的父节点
    private int[] elementSize;     // elementSize[i]表示以i为根的集合中元素个数

    public UnionFind(int size) {
        parent = new int[size];
        elementSize = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; //初始化的时候父节点值是本身 没有相交
            elementSize[i] = 1;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    // 查看元素p和元素q是否所属一个集合,直接看根节点是否一致
    // O(h)复杂度, h为树的高度
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(h)复杂度, h为树的高度
    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if( pRoot == qRoot )
            return;

        // 根据两个元素所在树的元素个数不同判断合并方向
        // 将元素个数少的集合合并到元素个数多的集合上
        if(elementSize[pRoot] < elementSize[qRoot]){
            parent[pRoot] = qRoot;  //少的集合 合并到多的集合上
            elementSize[qRoot] += elementSize[pRoot]; //多的集合中元素个数 ++
        }
        else{ // elementSize[qRoot] <= elementSize[pRoot]
            parent[qRoot] = pRoot;
            elementSize[pRoot] += elementSize[qRoot];
        }

    }

    //查找元素的根节点值
    private int find(int p) {
        if(p < 0 || p >= parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
        while(p != parent[p]) {
            p = parent[p];
        }

        return p;
    }
}
