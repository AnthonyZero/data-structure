import java.util.TreeMap;

//字典树
public class Trie {

    private Node root;

    public Trie() {
        root = new Node();
    }

    // 向Trie中添加一个新的单词word
    public void insert(String word) {
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
            cur.isWord = true;
        }
    }

    // 搜索是否存在此单词 以. 或者小写字母组成  .代表所有
    public boolean search(String word) {
        return match(root, word, 0);
    }

    //从node 节点出发 是否匹配word中第index个字符
    private boolean match(Node node, String word, int index) {
        if (index == word.length()) {
            return node.isWord; //已经到达word末尾
        }
        char c = word.charAt(index); //待匹配字符
        if (c != '.') {
            //正常字符
            if(node.next.get(c) != null) {
                //当前字符存在 看下一个字符
                return match(node.next.get(c), word, index + 1);
            } else {
                return false;
            }
        } else {
            //.匹配所有
            for (char nextChar : node.next.keySet()) {
                // 只要其中一个分叉 匹配成功 就算成功
                if (match(node.next.get(nextChar), word, index + 1)) {
                    return true;
                }
            }
            //.跳过当前这一层 没一个匹配成功
            return false;
        }
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
