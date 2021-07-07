package 矩阵;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huahan
 * date: 2021/6/24.
 * version:1.0
 */
public class q54_螺旋矩阵 {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> order = new ArrayList<Integer>();
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return order;
            }
            int rows = matrix.length, columns = matrix[0].length;
            int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
            while (left <= right && top <= bottom) {
                // 上首行
                for (int column = left; column <= right; column++) {
                    order.add(matrix[top][column]);
                }
                // 右首列
                for (int row = top + 1; row <= bottom; row++) {
                    order.add(matrix[row][right]);
                }
                // 打印 下首行 + 昨首列
                if (left < right && top < bottom) {
                    for (int column = right - 1; column > left; column--) {
                        order.add(matrix[bottom][column]);
                    }
                    for (int row = bottom; row > top; row--) {
                        order.add(matrix[row][left]);
                    }
                }
                left++;
                right--;
                top++;
                bottom--;
            }
            return order;
        }


    public static void main(String[] args) {
        int[][] nums= {{1,2,3},{4,5,6},{7,8,9}};
        q54_螺旋矩阵 solution = new q54_螺旋矩阵();
        System.out.println(solution.spiralOrder(nums));
    }
    }