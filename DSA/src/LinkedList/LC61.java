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
class LC61 {
    public ListNode rotateRight(ListNode head, int k) {
        if(k == 0 ||  head==null || head.next==null) return head;

        ListNode tail = head;
        ListNode prev = null;
        int length = 1;
        while (tail.next != null) {
            prev = tail;
            tail = tail.next;
            length++;
        }
        k = k%length;
        if(k==0) return head;

        tail.next = head;

        ListNode newtail = tail;


        for (int i = 0; i < length-k; i++) {
            newtail = newtail.next;
        }
        // Step 5: New head is after newTail
        ListNode newHead = newtail.next;

        // Step 6: Break the circle
        newtail.next = null;

        return newHead;
    }
}