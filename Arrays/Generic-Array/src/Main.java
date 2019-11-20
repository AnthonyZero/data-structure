public class Main {

    public static void main(String[] args) {
        Array<Integer> array = new Array(20);
        for (int i = 0; i < 10; i ++) {
            array.addLast(i);
        }
        array.add(0, 100);
        System.out.println(array);
        array.set(10, 10);
        System.out.println(array.get(10));

        array.remove(0);
        array.remove(array.getSize() - 2);
        System.out.println(array);

        array.removeElement(1);
        System.out.println(array);
    }
}
