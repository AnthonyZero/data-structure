public class BST<E extends Comparable<E>> { //节点可排序比较

    private Node root; //根节点
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e){
        root = add(root, e);
    }

    // 在二分搜索树中查找是否包含元素e
    public boolean contains(E e) {
        return contains(root, e);
    }


    // 向以node为根的二分搜索树中插入元素e，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, E e){
        if(node == null) {
            size ++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0) { //插入元素如果小于父节点
            node.left = add(node.left, e);  //父节点左指针 关联上
        } else if(e.compareTo(node.e) > 0){ //插入元素如果大于父节点
            node.right = add(node.right, e); //父节点右指针 关联上
        }

        return node;
    }

    // 看以node为根的二分搜索树中是否包含元素e, 递归算法
    private boolean contains(Node node, E e){
        if (node == null) { //找到最低层 没找到返回false
            return false;
        }

        if (e.compareTo(node.e) == 0) {  //递归下去的中途找到了返回true
            return true;
        } else if (e.compareTo(node.e) < 0) { //递归左子树
            return contains(node.left, e);
        } else {
            return contains(node.right, e); //递归右子树
        }
    }


    private class Node {
        public E e; //节点元素
        public Node left; //左子树
        public Node right; //右子树

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }
}
