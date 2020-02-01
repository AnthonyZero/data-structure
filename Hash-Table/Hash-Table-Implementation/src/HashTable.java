import java.util.TreeMap;

public class HashTable<K,V> {

    private TreeMap<K,V>[] hashTable;
    private int M; //哈希表的数组容量
    private int size;

    public HashTable(int M) {
        this.M = M;
        this.size = 0;
        this.hashTable = new TreeMap[M];
        for (int i = 0; i < M; i++) {
            hashTable[i] = new TreeMap<>();
        }
    }

    public HashTable() {
        this(97);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize() {
        return this.size;
    }

    public void add(K key, V value) {
        TreeMap<K,V> treeMap = hashTable[hash(key)];
        if (treeMap.containsKey(key)) {
            treeMap.put(key, value); //修改值
        } else {
            treeMap.put(key, value);
            size++;
        }
    }

    public V remove(K key) {
        TreeMap<K,V> map = hashTable[hash(key)]; //定位到哪个map
        V ret = null;
        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;
        }
        return ret;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashTable[hash(key)];
        if(!map.containsKey(key))
            throw new IllegalArgumentException(key + " doesn't exist!");

        map.put(key, value);
    }

    public boolean contains(K key){
        return hashTable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashTable[hash(key)].get(key);
    }
}
