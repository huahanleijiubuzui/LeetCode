package 动态规划.买卖股票的最佳时机;

/**
 * @author huahan
 * date: 2021/3/24.
 * version:1.0
 *
 *
 * desc:给定一个数组，他的第i个元素是一直给定股票第n天的价格
 * 设计一个算法来计算你能获得的最大利润，你可以尽可能的完成更多
 * 的交易（多次买卖同一只股票）
 *
 * 注意：你不能同时购买多笔股票，你必须在再次购买前清空手中的股票
 */
public class Solution {

    /**
     * 获取同一股票最大利润--动态规划
     * @param prices
     * @return
     *
     * 当前利润分为两种情况
     * 1：当前手中有股票
     * 2：当前手中没有股票
     *
     */
    public static int getMaxProfits(int[] prices) {
        if(prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        // 定义一个n行2列的数组，表示某一天是否持有股票的收益情况
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for(int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]-prices[i]);
        }
        return dp[n-1][0];
    }

    /**
     * 获取最大利润--贪心算法
     * @param prices
     */
    public static int getMaxProfits2(int[] prices) {
        if(prices == null || prices.length < 1) {
            return 0;
        }
        int maxProfit = 0;
        for(int i = 1; i < prices.length; i++) {
            maxProfit += Math.max(prices[i]-prices[i-1],0);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,1,4,5,6,3};
        System.out.println(getMaxProfits(nums));
        System.out.println(getMaxProfits2(nums));
    }
}
