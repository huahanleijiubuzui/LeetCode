package 动态规划.q5_最长回文子串.f1;

/**
 * o(n^2) 以每个字符为中心计算回文长度
 */
class Solution {
    /**
     * 计算最长回文串,以当前字符为中心，向前向后查找
     * @param s  字符串
     * @return
     */
    public static String getPalindrome(String s) {
        String rs = "";
        if(s == null || s == "") {
            return rs;
        }
        int length = s.length();
        for(int i =0 ; i< length; i++) {
            // 定义两个游标，向前向后走
            int m = i,n = i;
            String rs_tmp = s.charAt(i)+"";
            while(true) {
                --m;
                ++n;
                if(m <=0 || n >= length || s.charAt(m) != s.charAt(n)){
                    break;
                }
                rs_tmp = s.charAt(m)+rs_tmp+s.charAt(n);
            }
            if(rs_tmp.length() > rs.length()){
                rs = rs_tmp;
            }
        }
        return rs;
    }


    public static void main(String[] args) {
        String s = "abcdcbe123454321";
        System.out.println(getPalindrome(s));
    }
}
