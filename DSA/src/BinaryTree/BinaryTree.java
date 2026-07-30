package BinaryTree;
import java.util.Scanner;

public class BinaryTree {
    private Node root;
    class Node {
        private int val;
        private Node left;
        private Node right;

        public Node(int val){
            this.val = val;
        }
    }

    public void populate(Scanner scanner) {
        int val = scanner.nextInt();
        root = new Node(val);
        populate(scanner, root);

    }

    public void populate(Scanner scanner, Node node) {

        System.out.println("Do you want to enter Left of : "+node.val);
        boolean left = scanner.nextBoolean();
        if (left) {
            System.out.println("Enter the value : ");
            int val = scanner.nextInt();
            node.left = new Node(val);
            populate(scanner, node.left);
        }

        System.out.println("Do you want to enter right of : "+node.val);
        boolean right = scanner.nextBoolean();
        if (right) {
            System.out.println("Enter the value : ");
            int val = scanner.nextInt();
            node.right = new Node(val);
            populate(scanner, node.right);
        }
    }

    public void display() {
        System.out.println(root.val);
        display(root.left);
        display(root.right);

    }
    public void display(Node node) {
        if (node.left != null) {
            System.out.println(node.left.val);
            display(node.left);
        }
        if (node.right != null) {
            System.out.println(node.right.val);
            display(node.right);
        }
    }
    public void prettyDisplay(){
        prettyDisplay(root,0);

    }
    public void prettyDisplay(Node node,int level){
        if(node == null){
            return;
        }
        prettyDisplay(node.right,level+1);
        if(level!=0){
            for(int i=0;i<level;i++){
                System.out.print("|\t\t ");
            }
            System.out.println("|--->"+node.val);
        }else{
            System.out.println(node.val);
        }
        prettyDisplay(node.left,level+1);
    }

}
