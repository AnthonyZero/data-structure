import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        HashTable<Integer,Integer> hashTable = new HashTable<>();
        for (int i = 0; i < 100000; i++) {
            hashTable.add(random.nextInt(10000), 0);
        }
        System.out.println(hashTable.getSize());
        hashTable.remove(100);
        System.out.println(hashTable.contains(100));
        System.out.println(hashTable.getSize());
    }
}
