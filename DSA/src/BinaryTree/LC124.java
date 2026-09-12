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
    int globalMax;
    public int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE;
        helper(root);
        return globalMax;
    }

    private int helper(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = helper(node.left);
        int right = helper(node.right);

        left = Math.max(left, 0);
        right = Math.max(right, 0);

        // Complete path through this node
        int currentPath = node.val + left + right;

        globalMax = Math.max(globalMax, currentPath);

        // Best one-sided path to parent
        return node.val + Math.max(left, right);
    }
}