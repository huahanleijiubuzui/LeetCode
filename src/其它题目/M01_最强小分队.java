package 其它题目;

/**
 * @author huahan
 * date: 2021/4/2.
 * version:1.0
 *
 * 有一个阵营，里面有n 个小队(1<=n<=100)，每个小队都有他们的能力值
 * ai(0<=i
 * 现在有一个紧急情况，需要从这些小队中选出连续的几个小队，组成一个最强的
 * 团队。最强的团队的定义为这个团队的所有小队的平均能力值最高。如果有多个最强
 * 团队，则选包含小队最多的一个。
 * 现在请你写个程序，输出这个最强的团队包含的小队的个数。
 * 输入小队的数量n，和n 个数，分别代表各小队的能力值ai
 * 输出一个数表示这个最强团队包含的小队的个数。
 * 示例1
 * 输入：
 * 6
 * [1,2,3,3,2,1]
 * 输出：
 * 2
 */
public class M01_最强小分队 {

    public static void main(String[] args) {
        int n = 14;
        int[] num = new int[]{2,4,6,1,2,1,2,6,6,6,6,1,1,1};
        System.out.println(getBestNum(n,num));
    }

    /**
     * 获取最强小队个数
     * @param n  小队数量
     * @param attacks  小队攻击力
     * @return
     */
    public static int getBestNum(int n, int[] attacks) {
        if(n != attacks.length || n <= 0) {
            return -1;
        }
        if(n == 1) {
            return 1;
        }
        int maxAttack = 0;
        for(int k = 0; k < attacks.length; k++) {
            if(attacks[k] > maxAttack) {
                maxAttack = attacks[k];
            }
        }

        int[] tags = new int[attacks.length];
        for(int m = 0; m < attacks.length; m++) {
            if(attacks[m] == maxAttack) {
                tags[m] = 1;
            } else {
                tags[m] = 0;
            }
        }

        // 遍历连续1的个数
        int max = 0;
        for(int y = 0; y < tags.length; n++) {
            int end =y;
            boolean flag = true;
            while (flag && end < tags.length) {
                if(tags[end] == 1) {
                    end++;
                } else {
                    flag = false;
                }
            }
            if((end -y) > max) {
                max = (end-y);
            }
            y++;
        }
        return max;
    }
}
