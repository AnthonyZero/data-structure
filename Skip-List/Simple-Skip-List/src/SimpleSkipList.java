import java.util.Random;

public class SimpleSkipList {

    //节点类型
    private static final byte HEAD_NODE = (byte) -1;
    private static final byte DATA_NODE = (byte) 0;
    private static final byte TAIL_NODE = (byte) -1;


    private Node head; //头节点 最高一层的头
    private Node tail; //尾节点 最高一层的尾
    private int size;
    private int height; //高度 默认0层 实际代表有最低一层 0对应真实高度1
    private Random random; //随机函数 取决于是否扩高

    public SimpleSkipList() {
        head = new Node(null, HEAD_NODE);
        tail = new Node(null, TAIL_NODE);
        head.right = tail;
        tail.left = head;
        random = new Random(System.currentTimeMillis());
    }

    //核心：最后落到最底层 方便add元素的时候 使用此方法
    private Node find(Integer element) {
        Node cur = head;
        //不断寻找到最底层
        for(;;) {
            //元素值 比右边大 右移 比右边小 下移
            while(cur.right.type != TAIL_NODE && element >= cur.right.value) {
                //在同一层 不断右移
                cur = cur.right;
            }
            //同一层移动完之后 看是否可以下移
            if(cur.down != null) {
                //只有不是最后一层 就可以下移
                cur = cur.down;
            } else {
                //已经到最底层了 跳出循环
                break;
            }
        }
        //此时 cur的值value 可能是 cur.value <= element < cur.right.value(如果存在)
        return cur;
    }

    //是否存在此元素节点
    public boolean contains(Integer element) {
        Node node = this.find(element);
        return node.value == element;
    }

    //通过元素值返回节点 不存在返回空
    public Node get(Integer element) {
        Node node = this.find(element);
        if(node.value == element) {
            return node;
        } else {
            return null;
        }
    }

    //添加元素
    public void add(Integer element) {
        //新加节点 添加到最底层 左右节点之间
        Node leftNode = this.find(element);
        Node newNode =  new Node(element, DATA_NODE);
        //leftNode newNode rightNode
        newNode.left = leftNode;
        newNode.right = leftNode.right;
        leftNode.right.left = newNode;
        leftNode.right = newNode;

        //扩展层数
        int currentLevel = 0;
        while(random.nextDouble() < 0.5d) {  //这里简单随机

            if(currentLevel >= height) {
                //已经到最高处了
                //高度扩一层
                Node dummyHead = new Node(null, HEAD_NODE);
                Node dummyTail = new Node(null, TAIL_NODE);
                dummyHead.right = dummyTail;
                dummyHead.down = head;
                head.up = dummyHead;

                dummyTail.left = dummyHead;
                dummyTail.down = tail;
                tail.up = dummyTail;

                //新的头节点 尾节点 赋值
                head = dummyHead;
                tail = dummyTail;
                height++; //高度+1
            }


            //leftNode 上升到 找到一个有上级的节点上
            while(leftNode != null && leftNode.up == null) {
                leftNode = leftNode.left;
            }
            leftNode = leftNode.up;

            //在这一层添加一个节点 （元素值为element）
            Node upNode = new Node(element, DATA_NODE);
            //建立联系
            upNode.left = leftNode;
            upNode.right = leftNode.right;
            upNode.down = newNode; //上升了一级
            newNode.up = upNode;

            leftNode.right.left = upNode;
            leftNode.right = upNode;

            currentLevel++;
            newNode = upNode; //newNode 向上一级 继续看随机函数 是否继续上升
        }

        size ++;
    }

    //遍历
    public void dumpSkipList() {
        //实际高度为 height + 1
        System.out.println(String.format("Total height is [%d]", height + 1));
        Node dummyHead = head;
        for(int i = height + 1; i > 0; i--) {
            System.out.printf("Level [%d]:", i);
            //开始遍历
            Node cur = dummyHead.right;
            while(cur.type == DATA_NODE) {
                //数据节点 才输出
                System.out.printf("-> %d", cur.value);
                cur = cur.right;
            }

            //继续下一层
            dummyHead = dummyHead.down;
            System.out.println();
        }
        System.out.println("*************************");
    }

    //在跳表中删除一个值，如果element不存在，直接返回false. 如果存在多个element ，删除其中任意一个即可
    //每一层 此元素（如果存在） 删除
    public boolean remove(Integer element) {
        if(!contains(element)) {
            return false;
        }
        size --; //元素个数 -1
        Node targetNode = find(element); //要删除的节点
        while(targetNode != null) {
            Node nextDelNode = targetNode.up;

            targetNode.up = null;
            targetNode.down = null;
            Node leftNode = targetNode.left;
            leftNode.right = targetNode.right;
            targetNode.right.left = leftNode;

            targetNode = nextDelNode; //往上一层继续循环
        }

        //调整头节点 由于删除了元素 可能有些 高层的没有数据节点 删除
        Node newHead = head;
        Node newTail = tail;
        while(newHead.right.type != DATA_NODE) {
            Node downNode = newHead.down; //往下一层 赋值
            newTail = newTail.down;

            newHead.right.down = null; //这一层 没有数据节点 只有head 和tail
            newHead.down = null;
            newHead = downNode; //继续看下一层

            height--; //高度--
        }
        head = newHead;
        tail = newTail;
        return true;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize(){
        return this.size;
    }


    private static class Node {
        private Integer value; //节点值
        private Node up, down, left, right; //四个方向 上下左右
        private byte type; //节点类型
        public Node(Integer value, byte type) {
            this.value = value;
            this.type = type;
        }
        //默认是数据节点
        public Node(Integer value) {
            this.value = value;
            this.type = DATA_NODE;
        }
    }


    public static void main(String[] args) {
//        SimpleSkipList skipList = new SimpleSkipList();
//        skipList.add(10);
//        skipList.dumpSkipList();
//
//        skipList.add(5);
//        skipList.dumpSkipList();

//        SimpleSkipList skip = new SimpleSkipList();
//        Random random = new Random();
//        for(int i = 0; i < 100; i++) {
//            skip.add(random.nextInt(1000));
//        }
//        skip.dumpSkipList();

        SimpleSkipList skipList = new SimpleSkipList();
        int[] arr = new int[]{23,43,12,45,66,77,100,4,69,22,12,6,15,78,33,87,56,90,18,20};
        for(int i = 0; i < arr.length; i++) {
            skipList.add(arr[i]);
        }
        skipList.dumpSkipList();
        System.out.println(skipList.contains(100));
        System.out.println(skipList.remove(99));
        System.out.println(skipList.remove(33));
        System.out.println(skipList.remove(12));
        System.out.println(skipList.remove(56));
        skipList.dumpSkipList();

        arr = new int[]{30, 55, 33, 85, 10};
        for(int i = 0; i < arr.length; i++) {
            skipList.add(arr[i]);
        }
        skipList.dumpSkipList();

    }
}
