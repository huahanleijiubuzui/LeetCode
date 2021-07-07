package 题库.马蜂窝题库;

/**
 *
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 *
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 *  
 *
 * 示例 1：
 *  1  3  5  7
 *  10 11 16 20
 *  23 30 34 60
 *
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * 输出：true
 * 示例 2：
 *
 *  1  3  5  7
 *  10 11 16 20
 *  23 30 34 60
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * 输出：false
 *  
 *
 * 提示：
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -104 <= matrix[i][j], target <= 104
 *
 *
 *
 * @author huahan
 * date: 2021/6/29.
 * version:1.0
 */
public class q74_搜索二位矩阵 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int column = matrix[0].length;
        int now = 0;
        for(int row_start = 0; row_start < row; row_start ++) {
            for(int column_start = 0; column_start < column; column_start ++) {
                now = matrix[row_start][column_start];
                // 当前值比下一行值小，则只往右侧查找
                if(row_start + 1 < row && target < matrix[row_start+1][column_start]) {
                    row = row_start+1;
                }
                // 当前值比下一行值小，则只往右侧查找
                if(row_start + 1 < row && target >= matrix[row_start+1][column_start]) {
                    break;
                }
                if(matrix[row_start][column_start] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        int[][] matrix = {{1},{3}};
        q74_搜索二位矩阵 solution = new q74_搜索二位矩阵();
        System.out.println(solution.searchMatrix(matrix, 3));
    }
}
