package 动态规划.q64_最小路径和;

/**
 * 动态规划 dp(j)=grid(i,j)+min(dp(j),dp(j+1)) o(m*n)
 */
public class Solution {
    /** 倒序推导
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        // 列
        int[] dp = new int[grid[0].length];
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if (i == grid.length - 1 && j != grid[0].length - 1) {
                    dp[j] = grid[i][j] + dp[j + 1];
                } else if (j == grid[0].length - 1 && i != grid.length - 1) {
                    dp[j] = grid[i][j] + dp[j];
                } else if (j != grid[0].length - 1 && i != grid.length - 1) {
                    dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);

                } else {
                    dp[j] = grid[i][j];
                }
            }
        }
        return dp[0];
    }

    /**
     * 获取最小路径和
     * @param grid
     * @return
     */
    public int getMinSum(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        int[][] dp = new int[row][column];

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column ; j ++) {
                // 首行
                if(i == 0 && j != 0) {
                    dp[i][j] = grid[i][j]+ dp[i][j-1]; // 当前值+前一列值
                } else if( i != 0 && j == 0) { //首列
                    dp[i][j] = grid[i][j] + dp[i-1][j];
                } else if(i !=0 && j != 0) { // 非首行+非首列
                    dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) +grid[i][j];
                } else {
                    dp[i][j] = grid[i][j];
                }
            }
        }

        return dp[row-1][column-1];
    }


    public static void main(String[] args) {
        int[][] grid = {{1,2,3},{4,5,6},{7,8,9}};
        Solution solution = new Solution();
        System.out.println(solution.minPathSum(grid));
        System.out.println(solution.getMinSum(grid));
    }
}
