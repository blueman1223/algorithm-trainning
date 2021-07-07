// https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private int[] inorder;
    private int[] postorder;
    private final Map<Integer, Integer> inorderMap = new HashMap<>();
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0) {
            return null;
        }
        this.inorder = inorder;
        this.postorder = postorder;
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTree(0, inorder.length - 1, 0, postorder.length - 1);
    }

    /**
     * 由于java 不方便截取数组 所以重写方法参数
     */
    private TreeNode buildTree(int inStart, int inEnd, int postStart, int postEnd) {        
        
        if (inEnd < inStart || postEnd < postStart) {
            return null;
        }
        // 利用倒排索引优化查找
        int inRoot = inorderMap.get(postorder[postEnd]);

        // int inRoot = inStart;
        // 后序根在后面，所以从后往前搜索
        // for (inRoot = inStart; inRoot <= inEnd; inRoot++) {
        //     if (inorder[inRoot] == postorder[postEnd]) {
        //         break;
        //     }  
        // }

        // if (inEnd < inRoot || postEnd < postStart) {
        //     return null;
        // }
    
        TreeNode leftChild = buildTree(inStart, inRoot - 1, postStart, postStart + inRoot - inStart - 1);
        TreeNode rightChild = buildTree(inRoot + 1, inEnd, postStart + inRoot - inStart, postEnd - 1);
        TreeNode node = new TreeNode(inorder[inRoot], leftChild, rightChild);
        return node;
    }
}