public class Main {

    public static void main(String[] args){
        BSTMap<String,String> bstMap = new BSTMap<>();
        for(int i = 0; i < 5; i++) {
            bstMap.add("str" + i, "github" + i);
        }
        System.out.println(bstMap.contains("str4"));
        String value = bstMap.get("str0");
        System.out.println(value);
        bstMap.set("str0", "github set");
        System.out.println(bstMap.get("str0"));
        bstMap.remove("str2");
        System.out.println(bstMap.getSize());
    }
}
