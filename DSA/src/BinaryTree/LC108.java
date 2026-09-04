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
    public TreeNode sortedArrayToBST(int[] nums) {
        return populate(nums,0,nums.length-1);
    }
    public TreeNode populate(int[] arr , int start , int end){
        if(start>end){
            return null;
        }

        int mid = (start+end)/2;
        TreeNode node = new TreeNode(arr[mid]);

        node.left = populate(arr,start,mid-1);
        node.right = populate(arr,mid+1,end);

        return node;
    }
}

/*
my solution which was wrong

mistake
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return populate(nums,0,nums.length-1);

    }
    public TreeNode populate(int[] arr , int start , int end){
        if(start==end){

        // instead of start == end  i should take if(start>end) because i
        // am decreasing the end by 1 in each recursive call.

            return new TreeNode(arr[start]);
        }

        int mid = (start+end)/2;
        TreeNode node = new TreeNode(arr[mid]);

// if i am already taking mid (current node as = arr[mid]) then i should not include it in my recursive call
// node.left = populate(arr,start,mid-1);
// node.right = populate(arr,mid+1,end);

        node.left = populate(arr,start,mid);
        node.right = populate(arr,mid+1,end);

        return node;
    }
}
 */