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

/*Given the root of a binary tree, return the length of the diameter of the tree.
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
 * This path may or may not pass through the root.
 * The length of a path between two nodes is represented by the number of edges between them.
 */

class Solution {
    int diameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;

    }
    public int height(TreeNode node){
        if(node==null){
            return 0;
        }

        int left = height(node.left);
        int right = height(node.right);

        int dia = left+right;

        diameter = Math.max(dia,diameter);

        return Math.max(left,right)+1;
    }
}