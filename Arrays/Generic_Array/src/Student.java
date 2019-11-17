public class Student {

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Student(name = %s, age = %d)", name, age);
    }

    public static void main(String[] args) {
        Array<Student> array = new Array<>();
        array.addLast(new Student("bobo", 21));
        array.addLast(new Student("tom", 20));
        array.addFirst(new Student("json", 22));
        System.out.println(array);
    }
}
