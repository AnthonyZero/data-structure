

public class Array {

    private int[] data;
    private int size; //数组大小

    public Array(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    //数组容量 与数组大小区分开
    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(int element) {
//        if (size == data.length) {
//            throw new IllegalArgumentException("添加元素失败，数组已不能容纳任何元素");
//        }
//        data[size] = element;
//        size++;
        add(size, element);
    }

    public void addFirst(int element) {
        add(0, element);
    }

    //指定位置插入元素
    public void add(int index, int element) {
       if (size == data.length) {
           throw new IllegalArgumentException("添加元素失败，数组已不能容纳任何元素");
       }
       if (index < 0 || index > size) {
           throw new IllegalArgumentException("插入元素非法，index需在0到size之间");
       }
       for (int i = size - 1; i >= index; i-- ) {
           data[i + 1] = data[i];       //从index开始后面的元素依次向后移动
       }
       data[index] = element;
       size++;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]).append("");
        }
        return sb.toString();
    }
}
