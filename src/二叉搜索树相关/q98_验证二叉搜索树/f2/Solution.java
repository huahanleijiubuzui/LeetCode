package 二叉搜索树相关.q98_验证二叉搜索树.f2;

/**
 * 判断给出的上下限数字是否为树中的最小与最大数字
 *  测试用例：  读取方法，从左到右，从下往上
 *         3
 *        / \
 *       1   5
 *          / \
 *         4   6
 * 寻找上下界递归 o(n)
 */
public class Solution {
    public boolean valid(TreeNode root, Integer min, Integer max) {
        if (root == null) {
            return true;
        }
        int val = root.val;

        if (min != null && val < min) {
            return false;
        }
        if (max != null && val > max) {
            return false;
        }

        if (!valid(root.left, min, val)) {
            return false;
        }
        if (!valid(root.right, val, max)) {
            return false;
        }
        return true;
    }

    public boolean isValidBST(TreeNode root) {
        return valid(root, null, null);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(5);
        root.left = n1;
        root.right = n2;
        TreeNode n3 = new TreeNode(4);
        TreeNode n4 = new TreeNode(6);
        n2.left = n3;
        n2.right = n4;
        System.out.println(new Solution().valid(root,1,6));
    }
}
