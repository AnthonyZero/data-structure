import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int n = 1000000;

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for(int i = 0 ; i < n ; i ++)
            maxHeap.addElement(random.nextInt(Integer.MAX_VALUE));

        int[] arr = new int[n]; //堆排序 从大到小
        for(int i = 0 ; i < n ; i ++)
            arr[i] = maxHeap.extractMax();

        // 判断相邻元素是否符合我们排序结果
        for(int i = 1 ; i < n ; i ++)
            if(arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");

        System.out.println("Test MaxHeap completed.");
    }
}
