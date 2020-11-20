import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    @org.junit.Test
    public void testThreeSum() {
        int[] s = new int[]{-1, 0, 1, 2, -1, -4};
        List<List<Integer>> lists = threeSum(s);
        System.out.println("lists = " + lists);
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        // 假设输入：[-1, 0, 1, 2, -1, -4]
        //先对数组进行排序 数组一般的解法使用双指针解法 要使用双指针解法就必须是排好序的数组
        Arrays.sort(nums);//[-4,-1,-1,0,1,2]
        //数组最大的下标
        int len = nums.length - 1;
        //判断边界值
        if (nums[0] > 0 || nums[len] < 0) {//如果最小值大于0 那么不可能有相加等于0的，如果最大值<0 那么整个数组的值都是小于0 那么也不可能得到结果
            return lists;
        }
        //[-4,-1,-1,0,1,2]
        // L            R
        // i = L+1
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;//如果num[i]大于0 那么就没有必要在循环了 因为不会得到结果
            //判断 num[i] 和 num[i-1]是否重复 当前的值和前一个值如果重复则继续下一个循环没有必要在计算了
            if (i> 0 && nums[i] == nums[i - 1]) continue;
            int L = i + 1;
            int R = nums.length - 1;
            while (L < R) {
                int result = nums[L] + nums[R] + nums[i];
                System.out.println("result:" + result);
                if (result < 0) {
                    //如果得到的结果小于0则说明L要完右移动 增大值 同时要判断L是否重复
                    while (L < R && nums[L] == nums[L + 1]) L++;
                    L++;
                } else if (result > 0) {
                    //如果得到的结果大于0则说明R要往左移动 减小值 同时要判断R是否重复
                    while (L < R && nums[R] == nums[R - 1]) R--;
                    R--;
                } else {
                    //==0 说明得到结果了 同时还得去重
                    while (L < R && nums[L] == nums[L + 1]) L++;
                    L++;
                    while (L < R && nums[R] == nums[R - 1]) R--;
                    R--;
                    lists.add(Arrays.asList(nums[i], nums[L], nums[R]));
                }
            }
        }
        return lists;
    }
}
