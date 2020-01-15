import java.util.TreeMap;

//字典树
public class Trie {

    private Node root;
    private int size;


    public Trie() {
        root = new Node();
        size = 0;
    }


    //获取单词的数量
    public int getSize() {
        return size;
    }

    // 向Trie中添加一个新的单词word
    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                //说明当前没有c这个字符的分支 那么新建一个分支
                cur.next.put(c, new Node());
            }
            // 不论是否包含c这个字段（不包含会新建分支） 那么树都要下一个深度(只是c这个分支)
            cur = cur.next.get(c);
        }

        //cur到了这个单词的最后一个字符
        if (!cur.isWord) {
            //例如 先存在panda 添加pan
            //或者先存在pan 在添加pan 这是不能再size ++了 因为已经存在此单词了
            cur.isWord = true;
            size ++;
        }
    }

    // 查询单词word是否在Trie中
    public boolean contains(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        //return true; 不能直接返回true 因为可能我们查的单词可能是前缀 但是不是我们前面添加的单词
        // 比如只是添加了panda 不能说明pan 存在于Trie 因为我们从来添加过pan这个单词
        return cur.isWord;
    }

    // 查询是否在Trie中有单词以prefix为前缀
    public boolean startsWith(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

    private class Node {
        public boolean isWord; //是否是一个单词
        public TreeMap<Character,Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }
}
