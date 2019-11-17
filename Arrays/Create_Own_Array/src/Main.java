public class Main {

    public static void main(String[] args) {
        Array array = new Array(20);
        for (int i = 0; i < 10; i ++) {
            array.addLast(i);
        }
        array.add(0, 100);
        array.set(10, 10);
        System.out.println(array.get(10));

        array.remove(0);
        array.remove(array.getSize() - 1);

        array.removeElement(2);
        System.out.println(array);
    }
}
