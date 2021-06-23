package 二叉搜索树相关.二叉树遍历;


import 二叉搜索树相关.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huahan
 * date: 2021/6/17.
 * version:1.0
 */
public class BinaryTreeTraversal {

    public Queue<Integer> queue = new LinkedList<>();

    /**
     * 二叉树前序遍历
     * @param root
     * @return
     */
    public Queue<Integer> preTraversal(TreeNode root) {
        if(root == null) {
            return queue;
        }
        // 这一行在前，就是前序遍历，在中就是中序遍历
        preTraversal(root.left);
        queue.offer(root.val);
        preTraversal(root.right);
        return queue;
    }

    public Queue<Integer> sequenceTraversal(TreeNode root) {
        if(root == null) {
            return queue;
        }
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root);
        while(!nodes.isEmpty()) {
           // 取出头部元素
           TreeNode tmp =  nodes.poll();
           queue.offer(tmp.val);

           if(tmp.left != null) {
               nodes.offer(tmp.left);
           }
           if(tmp.right != null) {
               nodes.offer(tmp.right);
           }
        }
        return queue;
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(6);
        root.left = n1;
        root.right = n2;
        TreeNode n3 = new TreeNode(1);
        TreeNode n4 = new TreeNode(3);
        n1.left = n3;
        n1.right = n4;

        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(7);
        n2.left = n5;
        n2.right = n6;

        System.out.println(new BinaryTreeTraversal().preTraversal(root));
        System.out.println(new BinaryTreeTraversal().sequenceTraversal(root));
    }

}
