package 排序算法;

import java.util.Arrays;

/**
 * @author huahan
 * date: 2021/7/1.
 * version:1.0
 */
public class SelectSort {

    // 选择排序
    public void select(int[] arr) {
        if(arr == null) {
            return;
        }

        for(int m = arr.length-1; m > 0; m--) {
            int maxValue = 0;
            int maxIndex = 0;
            for(int n = 0; n <= m; n++) {
                if(arr[n] > maxValue) {
                    maxIndex = n;
                    maxValue = arr[n];
                }
            }
            arr[maxIndex] = arr[m];
            arr[m] = maxValue;
        }
    }

    //冒泡排序
    public void bubbleSort(int[] arr) {
        for(int j = 0; j < arr.length; j++) {
            for(int k = j+1; k < arr.length; k++) {
                if(arr[j] > arr[k]) {
                    swap(arr,j,k);
                }
            }
        }
    }

    public void swap(int[] arr,int j,int k) {
        int temp = arr[j];
        arr[j] = arr[k];
        arr[k] = temp;
    }

    public static void main(String[] args) {
        int[] arr= new int[]{1,5,7,3,4,8};
        SelectSort ss = new SelectSort();
        /* ss.select(arr);*/
        ss.bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
