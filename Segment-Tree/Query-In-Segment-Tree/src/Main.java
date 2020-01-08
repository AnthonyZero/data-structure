public class Main {

    public static void main(String[] args) {
        Integer[] nums = new Integer[]{-1, 1, 4, 0, 3, 2, -5};
        SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(nums, new Merger<Integer>() {
            @Override
            public Integer merger(Integer a, Integer b) {
                return a + b;
            }
        });

        //求某个区间的和 测试
        System.out.println(segmentTree.query(0, segmentTree.getSize() - 1));
        System.out.println(segmentTree.query(0, 2));
        System.out.println(segmentTree.query(1, 4));
        System.out.println(segmentTree.query(2,2));
        System.out.println(segmentTree.query(3, 5));
        System.out.println(segmentTree.query(1, 5));
    }
}
