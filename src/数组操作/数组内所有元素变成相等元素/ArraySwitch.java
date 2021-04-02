package 数组操作.数组内所有元素变成相等元素;

import org.junit.Test;

import java.util.Arrays;

/**
 * 题目：数组变换
 *
 * 定义一个长度为 n 的数组 a  和一个正整数 d
 *
 * 每次选择数组中的一个元素a[i] 将其变为 a[i]+d 或 a[i]-d 算一次操作
 *
 *  要求：将数组中所有元素变成相等元素，如果有解，输出最小操作次数，如果无解，输出 -1
 *
 *  数据范围：
 *  1<=n<=100000  1<=d<=100  1<=a[i]<=100000
 *
 * @author huahan
 * date: 2021/3/16.
 * version:1.0
 */
public class ArraySwitch {
    /**
     * 获取全部数据变为arr[targetIndex]的最小步骤
     * @param arr  有序数组
     * @param step  步长
     * @param targetIndex  下标
     * @return
     */
    public int getMinStepCount(int[] arr, int step, int targetIndex) {
        int count = -1;
        // 1、判断是否有解
        // 定义一个余数,则 arr[i] % step ; (arr[i]+step) % step ; (arr[i]-step) % step  余数都相等
        int remainder = arr[targetIndex] % step;
        for(int k =0; k< arr.length; k++) {
            boolean flag = arr[k] % step == remainder ? true : false;
            if(!flag) {
                return -1;
            }
        }
        int sumLeft = 0;
        int sumRight = 0;
        // 2、返回最小解步骤数
        for(int k =0; k< arr.length; k++) {
            if(k < targetIndex) {
                sumLeft += arr[k];
            } else {
                sumRight += arr[k];
            }
        }
        return (targetIndex * arr[targetIndex] - sumLeft) / step + (sumRight-(arr.length - targetIndex) *  arr[targetIndex]) / step;
    }


    @Test
    public void test() {
        int[] arr = new int[]{9,5,1,13,17,21};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(getMinStepCount(arr,4,0));
    }
}
