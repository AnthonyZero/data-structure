public class Main {

    // 有点分治的思想
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{-2, 1, 5, 4, -5, 6};

        //求和
        SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(arr, new Merger<Integer>() {
            @Override
            public Integer merger(Integer a, Integer b) {
                return a + b;
            }
        });

        System.out.println(segmentTree.toString());
    }
}
