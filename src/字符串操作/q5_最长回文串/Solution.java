package 字符串操作.q5_最长回文串;

/**
 * @author huahan
 * date: 2021/6/23.
 * version:1.0
 */
public class Solution {

    String ans = "";
    public String longestPalindrome(String s) {
        for (int i = 0; i < s.length(); i++) {
            // 回文子串长度是奇数,最中间是同一个数,所以取一个就行
            helper(i, i, s);
            // 回文子串长度是偶数,取两个数字
            helper(i, i + 1, s);
        }
        return ans;
    }

    /**
     * 获取回文串
     * @param start  开始下标
     * @param end   结束下标
     * @param s  原始字符串
     */
    public void helper(int start, int end, String s) {
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
        }
        // 注意此处m,n的值循环完后  是恰好不满足循环条件的时刻
        // 此时m到n的距离为n-m+1，但是mn两个边界不能取 所以应该取m+1到n-1的区间  长度是n-m-1
        if (end - start - 1 > ans.length()) {
            //substring要取[m+1,n-1]这个区间
            //end处的值不取,所以下面写的是n不是n-1
            ans = s.substring(start + 1, end);
        }
    }

    public static void main(String[] args) {
        String s = "abcd1cba11";
        Solution solution = new Solution();
        System.out.println(solution.longestPalindrome(s));
    }
}
