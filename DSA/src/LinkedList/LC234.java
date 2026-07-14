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
class LC234 {
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        ListNode newList =slow;
        ListNode prev = null;
        while (newList != null) {
            ListNode temp = newList.next;
            newList.next = prev;
            prev = newList;
            newList = temp;
        }

        while(prev != null) {
            if(fast.val!= prev.val) return false;
            prev =prev.next;
            fast = fast.next;
        }
        return true;
    }
}