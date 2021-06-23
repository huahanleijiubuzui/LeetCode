package hash相关.q387_字符串中的第一个唯一字符;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
        String s = "hhello,,weorld !";
        System.out.println(firstUniqChar(s));
        System.out.println(getUniqChar(s));
        Solution solution = new Solution();
        System.out.println(solution.getUniqFirstCharWithQueue(s));
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

    /**
     * 借助队列(先进先出)，一次循环找出结果
     * @param str
     * @return
     */
    public int getUniqFirstCharWithQueue(String str) {
        if(str == null || str == "") {
            return -1;
        }
        Queue<Pair> queue = new LinkedList();
        Map<Character, Integer> map = new HashMap();
        int n = str.length();
        char[] chars = str.toCharArray();

        for(int i = 0 ; i < n; i++) {
            if(map.containsKey(chars[i])) {
                map.put(chars[i],-1);
                // 判断重复的字符是否位于队列头部，是的话从队列中删除
                while(!queue.isEmpty() && map.get(queue.peek().ch) == -1) {
                    queue.poll();
                }
            } else {
                map.put(chars[i],1);
                queue.offer(new Pair(chars[i],i));
            }
        }
        return queue.isEmpty() ? -1 : queue.peek().index ;
    }

    class Pair{
        // 字符
        char ch;
        // 字符下标
        int index;

        Pair(Character ch, Integer index) {
            this.ch = ch;
            this.index = index;
        }
    }
}
