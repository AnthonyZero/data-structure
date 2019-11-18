

public class Array<E> {

    private E[] data;
    private int size; //数组大小

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
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

    public void addLast(E element) {
        add(size, element);
    }

    public void addFirst(E element) {
        add(0, element);
    }

    //指定位置插入元素
    public void add(int index, E element) {
       if (index < 0 || index > size) {
           throw new IllegalArgumentException("插入元素非法，index需在0到size之间");
       }
       if (size == data.length) {
           resize(2 * data.length);
       }
       for (int i = size - 1; i >= index; i-- ) {
           data[i + 1] = data[i];       //从index开始后面的元素依次向后移动
       }
       data[index] = element;
       size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size){
            throw new IllegalArgumentException("索引无效，数组越界");
        }
        return data[index];
    }

    public void set(int index, E element) {
       if (index < 0 || index >= size) {
           throw new IllegalArgumentException("索引无效，数组越界");
       }
       data[index] = element;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    //查找元素所在的索引
    public int find(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    //删除元素
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("删除失败，数组越界");
        }
        E ret = data[index];
        for (int i = index; i < size - 1; i ++) {
            data[i] = data[i + 1];
        }
        data[size] = null;
        size--;
        // 空间缩小
        if (size == data.length / 2) {
            resize(data.length / 2);
        }
        return ret;
    }


    public E removeFirst() {
        return remove(0);
    }


    public E removeLast() {
        return remove(size - 1);
    }

    public void removeElement(E element) {
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

    //扩容
    private void resize(int newCapacity) {
        E[] newData = (E[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}
