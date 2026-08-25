public class BST {

    class Node {
        private int value;
        private Node left;
        private Node right;
        private int height;

        public Node(int value){
            this.value = value;
        }
    }

    private Node root;

    private int height(Node node){
        if(node==null){
            return -1;
        }

        return node.height;
    }

    public BST(){

    }

    public void insert(int val){
        root = insert(root,val);
    }

    private Node insert(Node node,int val){

        if(node==null){
            return new Node(val);
        }

        if(val < node.value){
            node.left = insert(node.left,val);
        }
        if(val > node.value){
            node.right = insert(node.right,val);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        return node;
    }

    public void populate(int [] nums){
        for (int i = 0; i < nums.length; i++) {
            insert(nums[i]);
        }
    }

    public void display(){
        display(root,"Root Node : ");
    }

    private void display(Node node, String detail) {

        if(node==null){
            return;
        }
        System.out.println(detail+" "+node.value);

        display(node.left,"Left node of "+node.value+" : ");
        display(node.right,"Right node of "+node.value+" : ");
    }

    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node){
        if(node==null){
            return;
        }

        System.out.print(node.value+" - ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node node){
        if(node==null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.value+" - ");
        inOrder(node.right);
    }

    public void postOrder(){
        postOrder(root);
    }
    private void postOrder(Node node){
        if(node==null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value+" - ");
    }

}
