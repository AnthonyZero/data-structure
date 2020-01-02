//最大堆
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data; //动态数组 用于存储堆的元素 从0开始 0这个位置是根

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    // 返回堆中的元素个数
    public int size(){
        return data.getSize();
    }

    // 返回一个布尔值, 表示堆中是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index){
        if(index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index - 1) / 2;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index) {
        return index * 2 + 2;
    }


    // 向堆中添加元素
    public void addElement(E e) {
        data.addLast(e); //满二叉树 添加元素就安装层序遍历 从左到右依次添加
        siftUp(data.getSize() - 1);
    }


    // 指定哪个索引的元素 上浮
    private void siftUp(int index) {
        while(index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0) {
            // 它的父节点比它自己要小 不满足最大堆的条件 所以交换这两个值
            E temp = data.get(index);
            data.set(index, data.get(parent(index))); //当前节点 设置值
            data.set(parent(index), temp); //父节点 设置值

            index = parent(index);
        }
    }
}
