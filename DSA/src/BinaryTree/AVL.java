public class AVL {
// Important part of the code :
    // Updates heights whenever you change the structure of the tree
    // Rotations LL ,RR , LR ,RL

    class Node{
        private int value;
        private int height;
        private Node left;
        private Node right;

        public Node(int value){
            this.value = value;
            this.height= 0;
        }
    }

    private Node root;

    public AVL(){

    }
    public int getHeight(){
        return root.height;
    }

    private int height(Node node){
        if(node==null){
            return -1;
        }

        return node.height;
    }

    public void insert(int value){
        root = insert(root,value);
    }

    private Node insert(Node node , int value){
        if(node==null){
            return new Node(value);
        }

        if(value > node.value){
            node.right = insert(node.right,value);
        }
        if(value < node .value){
            node.left = insert(node.left,value);
        }

        node.height = 1 + Math.max(height(node.left),height(node.right));

        return rotate(node);
    }

    private Node rotate(Node p){

        //left heavy

        if(height(p.left)-height(p.right)>1){

            //LL
            if(height(p.left.left)-height(p.left.right)>0) {
                return rightRotate(p);
            }
            //LR
            else {
                p.left = leftRotate(p.left);
                return rightRotate(p);
            }
        }

        //Right heavy
        if(height(p.left)-height(p.right)<-1){
            if(height(p.right.left)-height(p.right.right)<0){
                return leftRotate(p);
            }
            //RL
            else{
                p.right = rightRotate(p.right);
                return leftRotate(p);
            }

        }

        return p;
    }

    private Node rightRotate(Node p){
        Node c = p.left;
        Node t = c.right;

        c.right = p;
        p.left = t;

        // UPDATE HEIGHTS
        p.height = 1 + Math.max(
                height(p.left),
                height(p.right)
        );

        c.height = 1 + Math.max(
                height(c.left),
                height(c.right)
        );

        return c;
    }

    private Node leftRotate(Node p){
        Node c = p.right;
        Node t = c.left;

        c.left = p;
        p.right = t;

        // UPDATE HEIGHTS
        p.height = 1 + Math.max(
                height(p.left),
                height(p.right)
        );

        c.height = 1 + Math.max(
                height(c.left),
                height(c.right)
        );

        return c;
    }

}
