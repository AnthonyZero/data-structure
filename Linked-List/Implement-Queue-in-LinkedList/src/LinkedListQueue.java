public class LinkedListQueue<E> implements AbstractQueue<E>{

    private Node head; //头部出队 O(1)
    private Node tail; //尾部入队 O(1)
    private int size;

    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) {
        if (isEmpty()) {
            head = new Node(e);
            tail = head;
        } else {
            Node node = new Node(e);
            tail.next = node;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("出队异常，队列为空");
        }
        Node ret = head;
        head = head.next;
        if (head == null) {
            tail = null; //如果只有一个元素出队 之后tail也会空
        }
        ret.next = null;
        size --;
        return (E)ret.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }
        return (E)head.e;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Queue: front ");

        Node cur = head;
        while(cur != null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
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
