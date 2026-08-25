Binary Search Tree (BST)

A BST is a binary tree where:

Left subtree contains values smaller than the current node.
Right subtree contains values greater than the current node.
Duplicate values are ignored in this implementation.
Node Structure
class Node {
int value;
Node left;
Node right;
int height;
}

Each node stores:

value → node's value
left → reference to left child
right → reference to right child
height → height of the node
Insertion
public void insert(int val){
root = insert(root, val);
}

Recursive insertion:

private Node insert(Node node, int val){

    if(node == null){
        return new Node(val);
    }

    if(val < node.value){
        node.left = insert(node.left, val);
    }

    if(val > node.value){
        node.right = insert(node.right, val);
    }

    node.height = 1 + Math.max(
        height(node.left),
        height(node.right)
    );

    return node;
}
Important recursion pattern
Go down → find empty position → insert
↓
return upward
↓
update heights

The returned node is reattached using:

node.left = insert(node.left, val);
node.right = insert(node.right, val);
Height
private int height(Node node){
if(node == null){
return -1;
}

    return node.height;
}

Height convention:

null → -1
leaf → 0

Height formula:

height(node) = 1 + max(height(left), height(right))

The height field is automatically initialized to 0 because Java initializes int instance variables to 0.

Populate BST
public void populate(int[] nums){
for(int num : nums){
insert(num);
}
}

Example:

int[] nums = {8, 3, 10, 1, 6, 14, 4, 7, 13};

Produces:

          8
        /   \
       3     10
      / \      \
     1   6      14
        / \     /
       4   7   13
Tree Traversals
1. Preorder

Root → Left → Right

private void preOrder(Node node){
if(node == null) return;

    System.out.print(node.value + " ");
    preOrder(node.left);
    preOrder(node.right);
}

Output:

8 3 1 6 4 7 10 14 13

Root is processed first → PREorder

2. Inorder

Left → Root → Right

private void inOrder(Node node){
if(node == null) return;

    inOrder(node.left);
    System.out.print(node.value + " ");
    inOrder(node.right);
}

Output:

1 3 4 6 7 8 10 13 14
Important BST property

Inorder traversal of a BST gives sorted order.

Root is processed in between → INorder

3. Postorder

Left → Right → Root

private void postOrder(Node node){
if(node == null) return;

    postOrder(node.left);
    postOrder(node.right);
    System.out.print(node.value + " ");
}

Output:

1 4 7 6 3 13 14 10 8

Root is processed after children → POSTorder

Traversal Cheat Sheet
PREORDER
Root → Left → Right

INORDER
Left → Root → Right

POSTORDER
Left → Right → Root

LEVEL ORDER
Level by level (BFS / Queue)
Easy way to remember

The only difference between the three DFS traversals is where the root is processed:

PREORDER   → ROOT comes first
INORDER    → ROOT comes in middle
POSTORDER  → ROOT comes last
Time Complexity

For insertion:

Average: O(log n)
Worst:   O(n)

For traversals:

O(n)

because every node is visited exactly once.