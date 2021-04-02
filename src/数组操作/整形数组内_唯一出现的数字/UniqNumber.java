package 数组操作.整形数组内_唯一出现的数字;

/**
 * @author huahan
 * date: 2021/3/24.
 * version:1.0
 *
 * desc : 给定一个非空整数数组，除了某个元素
 * 只出现一次外，其余每个元素均出现了2次，找出只出现一次的元素
 *
 *
 * 思路：异或操作，异或操作特点
 * 1：任何数与0异或结果都是本身
 * 2：任何数与自身异或结果都是0
 * 3：异或操作满足交互律与结合律
 *
 */
public class UniqNumber {
    public static int getUniqNum(int[] nums) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        if(nums.length < 2) {
            return nums[0];
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,6,5,4,2,1};
        System.out.println(getUniqNum(nums));
    }
}
