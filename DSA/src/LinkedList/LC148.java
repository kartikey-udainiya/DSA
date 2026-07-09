package LinkedList;

public class LC148 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode sortList(ListNode head){
            if (head == null || head.next == null) {
                return head;
            }
            ListNode mid = findMid(head);
            ListNode midNext = mid.next;
            mid.next = null;

            ListNode left = sortList(head);
            ListNode right = sortList(midNext);

            return merge(left,right);

        }
        public ListNode merge(ListNode left, ListNode right){
            if(left==null){
                return right;
            }
            if(right == null){
                return left;
            }

            if(left.val<right.val){
                left.next= merge(left.next,right);
                return left;
            }else{
                right.next = merge(left,right.next);
                return right;
            }
        }
        public ListNode findMid(ListNode head){
            ListNode slow = head;
            ListNode fast = slow.next;
            int count = 0 ;
            while(fast!=null && fast.next!=null){
                slow = slow.next;
                fast = fast.next.next;

                System.out.println(count);
                count++;

            }
            return slow;
        }
    }
}
