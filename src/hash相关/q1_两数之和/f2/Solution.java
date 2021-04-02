package hash相关.q1_两数之和.f2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 一遍hash o(n)
 */
public class Solution {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }


    public static void main(String[] args) {
        int [] nums = new int[]{1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(twoSum(nums,8)));
    }
}
