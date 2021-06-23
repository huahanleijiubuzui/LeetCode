package 题库.去哪儿网题库;

import 二叉搜索树相关.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * @author huahan
 * date: 2021/6/22.
 * version:1.0
 */
public class 根据中序遍历与后续遍历构建二叉树 {

    Map<Integer,Integer> map = new HashMap<>();
    int rootIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
        rootIndex = postorder.length - 1;
        return build(0, postorder.length - 1, postorder);
    }

    public TreeNode build(int left, int right, int[] postorder){
        if(left > right) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[rootIndex]);
        int mid = map.get(postorder[rootIndex]);
        rootIndex--;
        root.right = build(mid + 1, right, postorder);
        root.left = build(left, mid - 1, postorder);
        return root;
    }

    public static void main(String[] args) {
        int[] inOrder = new int[]{9,3,15,20,7};
        int[] postOrder = new int[]{9,15,7,20,3};
        根据中序遍历与后续遍历构建二叉树 solution = new 根据中序遍历与后续遍历构建二叉树();
        TreeNode treeNode = solution.buildTree(inOrder,postOrder);
        System.out.println(treeNode);
    }
}
