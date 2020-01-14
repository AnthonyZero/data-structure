public class Main {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("pan");
        trie.add("panda");
        trie.add("book");
        trie.add("bookstore");
        trie.add("cake");
        System.out.println(trie.contains("book"));
        System.out.println(trie.contains("cake"));
        System.out.println(trie.contains("pand"));
        trie.add("pand");
        System.out.println(trie.contains("pand"));
        System.out.println(trie.contains("pan"));
        System.out.println(trie.contains("panda"));
    }
}
