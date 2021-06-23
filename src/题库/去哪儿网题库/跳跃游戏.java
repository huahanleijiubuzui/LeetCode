package 题库.去哪儿网题库;

/**
 * 【mid】
 * 45. 跳跃游戏 II
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 假设你总是可以到达数组的最后一个位置。
 *
 * 示例 1:
 *
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * 示例 2:
 *
 * 输入: [2,3,0,1,4]
 * 输出: 2
 *
 *
 * 提示:
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 105
 *
 * date: 2021/6/22.
 * version:1.0
 */
public class 跳跃游戏 {

    public int getMinStep(int[] array){
        if(array.length < 2) {
            return 1;
        }
        // 下标+当前下标值 > 最大下标，即为到达最后一个元素
        int position = array.length-1;
        int step = 0;
        while (position > 0) {
            for(int i = 0; i <array.length; i++) {
                if(i + array[i] >= position) {
                    position = i;
                    step++;
                    break;
                }
            }
        }
        return step;
    }

    /**
     * 复杂度为 o(n)，不好理解，end为边界值，即从第一步能到达的返回内
     * 获取的边界下标，每次到达边界，步数+1
     * @param array
     * @return
     */
    public int getMinStep1(int[] array) {
        int length = array.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + array[i]);
            if (i == end) {
                end = maxPosition;
                System.out.println(end);
                steps++;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        跳跃游戏 jump = new 跳跃游戏();
        int num[] = new int[]{2,3,0,1,4,5,7,1,1,1,1,1,1,1,1};
        System.out.println(jump.getMinStep(num));
        System.out.println(jump.getMinStep1(num));
    }
}
