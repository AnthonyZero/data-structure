public class Main {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("bad");
        trie.insert("dad");
        trie.insert("mad");
        System.out.println(trie.search("pad"));
        System.out.println(trie.search("bad"));
        System.out.println(trie.search(".ad")); //通配符搜索
        System.out.println(trie.search("b.."));
    }
}
