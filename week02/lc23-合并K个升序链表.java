// https://leetcode-cn.com/problems/merge-k-sorted-lists/

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
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        if (lists.length == 2) {
            return mergeTwoList(lists[0], lists[1]);
        }

        ListNode[] group1 = new ListNode[lists.length / 2];
        System.arraycopy(lists, 0, group1, 0, group1.length);
        ListNode list1 = mergeKLists(group1);
        int midIndex = lists.length / 2;
        if (lists.length % 2 > 0) {
            list1 = mergeTwoList(list1, lists[lists.length / 2]);
            midIndex++;
        }

        ListNode[] group2 = new ListNode[lists.length / 2];
        System.arraycopy(lists, midIndex, group2, 0, group2.length);
        ListNode list2 = mergeKLists(group2);
        return mergeTwoList(list1, list2);

    }

    private ListNode mergeTwoList(ListNode head1, ListNode head2) {
        ListNode hair = new ListNode();
        ListNode head = hair;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                head.next = head1;
                head1 = head1.next;
            } else {
                head.next = head2;
                head2 = head2.next;
            }
            head = head.next;
        }
        if (head1 != null) {
            head.next = head1;
        } else {
            head.next = head2;
        }
        return hair.next;
    }
}