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
class LC143 {
    public void reorderList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        ListNode temp = slow.next;
        slow.next = null;
        ListNode prev = null;
        while (temp != null) {
            ListNode next = temp.next;
            temp.next = prev;
            prev = temp;
            temp = next;
        }

        ListNode l1 = fast;
        ListNode l2 = prev;
        ListNode dummy = new ListNode(0);
        boolean s = true;
        ListNode cur = dummy;
        while (l1!=null &&l2!=null) {
            if (s) {
                dummy.next = l1;
                dummy = dummy.next;
                l1 = l1.next;
                s = false;
            }else{
                dummy.next = l2;
                dummy = dummy.next;
                l2 = l2.next;
                s = true;
            }

            if(l1==null){
                dummy.next = l2;
            }
            if(l2==null){
                dummy.next = l1;
            }
        }
    }
}