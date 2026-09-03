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
/*
Given the root of a binary tree with unique values and the values of two different nodes
of the tree x and y, return true if the nodes corresponding to the values x and y in the tree
are cousins, or false otherwise.
Two nodes of a binary tree are cousins if they have the same depth with different parents.
Note that in a binary tree, the root node is at the depth 0, and children of each depth k node
are at the depth k + 1.
 */

class Solution {
    public boolean isCousins(TreeNode root, int x, int y) {
        return depth(root, x, 0) == depth(root, y, 0)
                && !sameParent(root, x, y);
    }

    public int depth(TreeNode node, int z, int level) {
        if (node == null) {
            return -1;
        }
        if (node.val == z) {
            return level;
        }

        int left = depth(node.left, z, level + 1);

        if (left != -1) {
            return left;
        }

        return depth(node.right, z, level + 1);
    }

    public boolean sameParent(TreeNode node, int x, int y) {
        if (node == null) {
            return false;
        }

        if (node.left != null && node.right != null) {
            if ((node.left.val == x && node.right.val == y) ||
                    (node.left.val == y && node.right.val == x)) {
                return true;
            }
        }

        return sameParent(node.left, x, y) ||
                sameParent(node.right, x, y);
    }
}
