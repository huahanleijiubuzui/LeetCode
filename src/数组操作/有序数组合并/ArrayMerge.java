package 数组操作.有序数组合并;

import java.util.Arrays;

/**
 * 两个有序数组合并
 * @author huahan
 * date: 2021/6/23.
 * version:1.0
 */
public class ArrayMerge {

    public int[] meger(int[] a, int[] b) {
        if(a == null) {
            return b;
        }
        if(b == null) {
            return a;
        }
        int length = Math.max(a.length,b.length);
        int[] c = new int[a.length+b.length];

        int i = 0; int j =0; int count = 0;
        while(count < (a.length+b.length) ) {
            if(i < a.length && (j >= b.length || a[i] < b[j])) {
                c[count++] = a[i++];
            } else {
                c[count++] = b[j++];
            }
        }
        return c;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,3,5,7};
        int[] b = new int[]{4,6,8,9,10};
        ArrayMerge arrayMerge = new ArrayMerge();
        System.out.println(Arrays.toString(arrayMerge.meger(a,b)));
    }
}
