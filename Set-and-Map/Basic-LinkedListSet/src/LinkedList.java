public class LinkedList<E> {

    private Node dummyHead; //虚拟头节点 元素为空 用户不可知
    private int size;

    public LinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向链表插入指定位置
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        if(index < 0 || index > size)
            throw new IllegalArgumentException("添加失败，index无效");

        Node pre = dummyHead;
        //pre指向index索引前一个节点
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
//            Node node = new Node(e);
//            node.next = pre.next;
//            pre.next = node;
        pre.next = new Node(e, pre.next);
        size++;

    }

    //链表头添加元素
    public void addFirst(E e) {
        add(0, e);
    }

    //先链表插入末尾元素
    public void addLast(E e) {
        add(size, e);
    }

    public E get(int index) {
        if(index < 0 || index > size)
            throw new IllegalArgumentException("失败，非法索引");
        Node cur = dummyHead.next;
        for (int i = 0; i< index; i++) {
            cur = cur.next;
        }
        return (E) cur.e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    //更新
    public void set(int index, E e) {
        if(index < 0 || index > size)
            throw new IllegalArgumentException("失败，非法索引");
        Node cur = dummyHead.next;
        for (int i = 0; i< index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    //查找链表中是否存在该元素
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while(cur != null) {
            if (cur.e.equals(e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    //删除元素
    public E remove(int index) {
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("删除失败，索引非法");

        Node pre = dummyHead; //待删除元素前面一个
        for (int i = 0; i< index; i++) {
            pre = pre.next;
        }
        Node delNode = pre.next; //待删除元素位置
        E ret = (E) delNode.e;

        pre.next = delNode.next;
        delNode.next = null;
        size--;
        return ret;
    }

    // 从链表中删除第一个元素, 返回删除的元素
    public E removeFirst(){
        return remove(0);
    }

    // 从链表中删除最后一个元素, 返回删除的元素
    public E removeLast(){
        return remove(size - 1);
    }


    // 从链表中删除元素e
    public void removeElement(E e){

        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.e.equals(e)) {
                break;
            }
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size --;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for(Node cur = dummyHead.next ; cur != null ; cur = cur.next) {
            res.append(cur + "->");
        }
        res.append("NULL");

        return res.toString();
    }

    //节点
    private class Node<E> {
        public E e;
        public Node next;

        public Node(E e,Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }
}
