public class SegmentTree<E> {

    private E[] tree;  //根据业务决定后 建立的线段树 的数组
    private E[] data;  //元素数据
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger){
        this.merger = merger;

        data = (E[])new Object[arr.length];
        for(int i = 0 ; i < arr.length ; i ++)
            data[i] = arr[i];

        tree = (E[])new Object[4 * arr.length]; //4n容量

        // 从树根开始递归 根据原数据来构造 线段树 tree[0] tree[1] tree[2]
        buildSegmentTree(0, 0, data.length - 1);
    }


    // 在treeIndex的位置创建表示区间[l...r]的线段树
    private void buildSegmentTree(int treeIndex, int left, int right) {
        if (left == right) {
            //递归到底部 这里建立叶子节点 此时节点的值 就是原数据数组某个索引的值
            tree[treeIndex] = data[left];
            return;
        }

        int leftChildIndex = leftChild(treeIndex); //左子树所在tree数组中索引
        int rightChildIndex = rightChild(treeIndex); // 右子树所在tree数组中索引

        // int mid = (l + r) / 2 可能溢出;
        int middle = left + (right - left) / 2;
        buildSegmentTree(leftChildIndex, left, middle);
        buildSegmentTree(rightChildIndex, middle + 1, right);

        //根据业务来 merge可以求和 求最大值等等  从这里递归返回的过程 就是从底往上建树的过程
        tree[treeIndex] = merger.merger(tree[leftChildIndex], tree[rightChildIndex]);
    }

    public int getSize(){
        return data.length;
    }

    public E get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal.");
        return data[index];
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return 2*index + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index){
        return 2*index + 2;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0 ; i < tree.length ; i ++){
            if(tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if(i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }
}
