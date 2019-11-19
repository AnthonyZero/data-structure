public interface AbstractStack<E> {
    int getSize();

    boolean isEmpty();

    void push(E e);

    E pop();

    E peek();

}
