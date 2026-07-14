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
class LC206 {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
// Reversing a list using recursion.
// public static ListNode reverseRecursive(ListNode head) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//        ListNode newHead = reverseRecursive(head.next);
//        head.next.next = head;
//        head.next = null;
//        return newHead;
//    }