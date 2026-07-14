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
class LC92 {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        for (int i = 0; i < left-1; i++) {
            pre = pre.next;
        }
//        printList(pre);
        ListNode leftpart = pre.next;
        ListNode cur = pre.next;

        pre.next = null;

        ListNode prev = null;

        for (int i = 0; i < right-left+1; i++) {
            ListNode temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        ListNode firstPart = pre;
        ListNode secondPart = prev;
        ListNode thirdPart = cur;

        pre.next=prev;
        leftpart.next=cur;
        return dummy.next;


    }
}