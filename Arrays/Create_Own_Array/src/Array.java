

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

    public int get(int index) {
        if (index < 0 || index >= size){
            throw new IllegalArgumentException("索引无效，数组越界");
        }
        return data[index];
    }

    public void set(int index, int element) {
       if (index < 0 || index >= size) {
           throw new IllegalArgumentException("索引无效，数组越界");
       }
       data[index] = element;
    }

    public boolean contains(int element) {
        for (int i = 0; i < size; i++) {
            if (data[i] == element) {
                return true;
            }
        }
        return false;
    }

    public int find(int element) {
        for (int i = 0; i < size; i++) {
            if (data[i] == element) {
                return i;
            }
        }
        return -1;
    }

    //删除元素
    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("删除失败，数组越界");
        }
        int ret = data[index];
        for (int i = index; i < size - 1; i ++) {
            data[i] = data[i + 1];
        }
        size--;
        return ret;
    }


    public int removeFirst() {
        return remove(0);
    }


    public int removeLast() {
        return remove(size - 1);
    }

    public void removeElement(int element) {
        int index = find(element);
        if (index != -1) {
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.format("size = %d, capacity = %d\n", size, data.length));
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i != size - 1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
