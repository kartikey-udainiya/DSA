package LinkedList;

public class LC876 {
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
        public ListNode middleNode(ListNode head) {
//Basic approach
            // int count = 0;
            // ListNode a = new ListNode();
            // a=head;

            // while(a!=null){
            //     a=a.next;
            //     count++;
            // }
            // a=head;
            // int middle = count/2 ;
            // for(int i = 0;i<middle;i++){
            //     a=a.next;
            // }

            //fast and slow pointer approach
            ListNode slow = head;
            ListNode fast = head;

            while(fast!=null && fast.next!=null){
                slow=slow.next;
                fast= fast.next.next;
            }

            return slow;

        }
    }
}
