public class SegmentTree {

    class Node{
        private int value;
        private int startInterval;
        private int endInterval;
        private Node left;
        private Node right;

        public Node(int startInterval, int endInterval){
            this.startInterval=startInterval;
            this.endInterval= endInterval;
        }
    }
    private Node root;

    public SegmentTree(){

    }

    public void populate(int[] arr){
        root = construct(arr , 0 , arr.length-1);
    }

    private Node construct(int arr [], int start, int end){
        if(start==end){
            Node leaf = new Node(start,end);
            leaf.value=arr[start];
            return leaf;
        }

        Node node = new Node(start,end);

        int mid = (start+end)/2;

        node.left = construct(arr,start,mid);
        node.right = construct(arr,mid+1,end);

        node.value = node.left.value + node.right.value;

        return node;

    }

    public int query(int start , int end){
        return query(root,start,end);
    }

    private int query(Node node,int start, int end){
        // completely inside
        if(start <= node.startInterval && node.endInterval <= end) {
            return node.value;
        }
        // completely outside
        if(node.startInterval > end || node.endInterval < start) {
            return 0;
        }
        // partial overlap
        return query(node.left, start, end)
                + query(node.right, start, end);
    }

    public void update(int value , int index){
        update(root,value,index);
    }

    private int update(Node node, int value , int index){

        //index should be in b/w the node.start and node.end range
        if(node.startInterval<=index && node.endInterval>=index){

            if(node.startInterval==index && node.endInterval==index){
                node.value=value;
                return node.value;
            }else{
                int leftans = update(node.left,value,index);
                int rightans = update(node.right,value,index);

                node.value = leftans + rightans;
                return node.value;
            }
        }

        return node.value;

    }
}
