public class Main {

    public static void main(String[] args) {
        Array<Integer> array = new Array();
        for (int i = 0; i < 10; i ++) {
            array.addLast(i);
        }
        System.out.println(array);
        array.add(0, 100);
        array.addLast(200);
        System.out.println(array);

        array.remove(1);
        System.out.println(array);
        array.removeLast();
        System.out.println(array);
    }
}
