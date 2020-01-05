import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Solution {

//    前 K 个高频元素
//
//    给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
//
//    示例 1:
//
//    输入: nums = [1,1,1,2,2,3], k = 2
//    输出: [1,2]
//    示例 2:
//
//    输入: nums = [1], k = 1
//    输出: [1]


    private class Freq implements Comparable<Freq>{

        public int e, freq; //元素e 和 元素出现的频率

        public Freq(int e, int freq){
            this.e = e;
            this.freq = freq;
        }
        //最大堆 堆顶 是频率最小的
        @Override
        public int compareTo(Freq another){
            if(this.freq < another.freq)
                return 1;
            else if(this.freq > another.freq)
                return -1;
            else
                return 0;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new TreeMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        //优先队列 一致维持k个元素 这k个元素即是我们的结果
        PriorityQueue<Freq> queue = new PriorityQueue<>(); //优先队列 频率最小的在队头
        for (int key : map.keySet()) {
            if (queue.getSize() < k) {
                queue.enqueue(new Freq(key, map.get(key)));
            } else if (map.get(key) > queue.getFront().freq){
                //当前元素频率 大于此时k个元素队列 队头频率
                queue.dequeue();
                queue.enqueue(new Freq(key, map.get(key)));
            }
        }

        List<Integer> list = new LinkedList<>();
        while(!queue.isEmpty()) {
            list.add(queue.dequeue().e);
        }
        return list;
    }

    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 2, 3, 3, 4, 5, 6, 7, 7, 7};
        int k = 3;
        System.out.println(((new Solution()).topKFrequent(nums, k)));
    }
}
