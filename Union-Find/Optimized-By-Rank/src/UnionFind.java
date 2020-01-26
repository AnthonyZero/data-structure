public class UnionFind implements UF {

    private int[] parent; //parent[i]表示第一个元素所指向的父节点
    private int[] rank;   // rank[i]表示以i为根的集合所表示的树的层数
    // 合并的时候树深度低的 合并到树深度高的上 宗旨在于树的深度最低最好

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; //初始化的时候父节点值是本身 没有相交
            rank[i] = 1;
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

        // 根据两个元素所在树的rank不同判断合并方向
        // 将rank低的集合合并到rank高的集合上 (树深度低的合并到树深度高的，此时树深度高的的深度还是保证不变)
        if(rank[pRoot] < rank[qRoot])
            parent[pRoot] = qRoot; //深度高的树 此时深度没有变化 不维护rank
        else if(rank[qRoot] < rank[pRoot])
            parent[qRoot] = pRoot;
        else{ // rank[pRoot] == rank[qRoot]
            parent[pRoot] = qRoot;
            rank[qRoot] += 1;   // 为什么只加1 比如两个深度为3，合并后其实深度只是高了一层
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
