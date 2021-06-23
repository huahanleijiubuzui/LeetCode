package 排序算法;

import java.util.Arrays;

/**
 * @author huahan
 * date: 2021/6/23.
 * version:1.0
 */
public class QuickSort {
    int partition(int a[], int low, int high){
        int key = a[low];
        while( low < high ){
            while(low < high && a[high] >= key) high--;
            a[low] = a[high];
            while(low < high && a[low] <= key) low++;
            a[high] = a[low];
        }
        a[low] = key;
        return low;
    }
    void quick_sort(int a[], int low, int high){
        if(low >= high) return;
        int keypos = partition(a, low, high);
        quick_sort(a, low, keypos-1);
        quick_sort(a, keypos+1, high);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,9,8,6,5};
        QuickSort sort = new QuickSort();
        sort.quick_sort(nums,0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
}
