public class Main {

    public static void main(String[] args) {
        LinkedListMap<String,String> linkedListMap = new LinkedListMap<>();
        for(int i = 0; i < 5; i++) {
            linkedListMap.add("str" + i, "github" + i);
        }
        System.out.println(linkedListMap.contains("str4"));
        String value = linkedListMap.get("str0");
        System.out.println(value);
        linkedListMap.set("str0", "github set");
        System.out.println(linkedListMap.get("str0"));
        linkedListMap.remove("str2");
        System.out.println(linkedListMap.getSize());
    }
}
