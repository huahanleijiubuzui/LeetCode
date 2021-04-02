package hash相关.q387_字符串中的第一个唯一字符;

import java.util.HashMap;
import java.util.Map;

/**
 * Hash o(n)
 */
public class Solution {

    public static int firstUniqChar(String s) {
        HashMap<Character, Integer> count = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < n; i++) {
            if (count.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        String s = "hello,world !";
        System.out.println(firstUniqChar(s));
        System.out.println(getUniqChar(s));
    }



    public static int getUniqChar(String s) {
        if(s == "" || s == null){
            return -1;
        }
        Map<Character,Integer> map = new HashMap<>();

        char[] chars  = s.toCharArray();

        for(Character c : chars){
             map.put(c, map.getOrDefault(c,0)+1);
        }

        int n = s.length();
        for(int i = 0; i < n; i++) {
            Character current = s.charAt(i);
            if(map.get(current) == 1){
                return i;
            }
        }
        return -1;
    }
}
