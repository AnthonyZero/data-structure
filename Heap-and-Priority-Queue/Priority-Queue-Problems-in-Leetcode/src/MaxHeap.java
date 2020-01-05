//最大堆
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data; //动态数组 用于存储堆的元素 从0开始 0这个位置是根

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    public MaxHeap(E[] arr){
        data = new Array<>(arr);
        for(int i = parent(arr.length - 1) ; i >= 0 ; i --)
            siftDown(i);
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

    // 获取堆中最大的元素
    public E findMax() {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("堆为空，获取最大元素失败");
        }
        return data.get(0);
    }

    public E extractMax() {
        E ret = findMax();

        // 交换第0个 和最后一个元素
        E temp = data.get(data.getSize() - 1);
        data.set(data.getSize() - 1, ret);
        data.set(0, temp);

        //删除最大的元素
        data.removeLast();

        siftDown(0);

        return ret;
    }

    // 指定哪个索引的元素 下沉
    private void siftDown(int index) {
        while (leftChild(index) < data.getSize()) {
            // 当左孩子索引没有越界 说明存在左还是 可以下沉
            int maxIndex = leftChild(index); //先假定maxIndex是左右孩子较大的元素索引

            //存在右孩子 更新maxIndex
            if (maxIndex + 1 < data.getSize() &&
                data.get(maxIndex + 1).compareTo(data.get(maxIndex)) > 0) {
                //并且右孩子 元素大于左孩子元素 更新maxIndex;
                maxIndex = maxIndex + 1; //此时是右孩子索引
            }

            //比较当前下沉元素 和左右孩子元素较大值 看是否替换
            if (data.get(index).compareTo(data.get(maxIndex)) >= 0) {
                break; //大于等于 退出完成 当前节点已经大于左右孩子节点
            }

            //替换
            E temp = data.get(index);
            data.set(index, data.get(maxIndex));
            data.set(maxIndex, temp);

            //更新index 继续循环
            index = maxIndex;
        }
    }


    // 取出堆中的最大元素，并且替换成元素e
    public E replace(E e){

        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }
}
