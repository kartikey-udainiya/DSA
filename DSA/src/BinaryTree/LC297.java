/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        helper1(root,sb);
        return sb.toString();
    }
    public void helper1(TreeNode node,StringBuilder sb){
        if(node==null){
            sb.append("n,");
            return;
        }

        sb.append(node.val).append(",");

        helper1(node.left,sb);
        helper1(node.right,sb);

    }
    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");

        return helper2(arr,new int[]{0});
    }
    public TreeNode helper2(String arr[],int[] index){
        if(arr[index[0]].equals("n")){
            index[0]++;
            return null;
        }

        TreeNode node = new TreeNode(
                Integer.parseInt(arr[index[0]])
        );

        index[0]++;

        node.left= helper2(arr,index);
        node.right = helper2(arr,index);

        return node;
    }
}