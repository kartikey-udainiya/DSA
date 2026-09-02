/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║ LeetCode 116 — Populating Next Right Pointers              ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * PROBLEM
 * ───────
 * You are given a PERFECT binary tree.
 *
 * Each node contains:
 *
 *     int val
 *     Node left
 *     Node right
 *     Node next
 *
 * Initially, every `next` pointer is NULL.
 *
 * TASK
 * ────
 * Set each node's `next` pointer to the next node on its
 * RIGHT at the SAME LEVEL.
 *
 * If there is no node to the right, `next` should remain NULL.
 *
 *
 * EXAMPLE
 * ───────
 *
 * Before:
 *
 *             1
 *           /   \
 *          2     3
 *         / \   / \
 *        4   5 6   7
 *
 *
 * After:
 *
 *             1 → NULL
 *           /   \
 *          2 ─→ 3 → NULL
 *         / \   / \
 *        4 → 5 → 6 → 7 → NULL
 *
 *
 * KEY IDEA
 * ────────
 * 1. Connect left child → right child.
 * 2. Connect right child → next node's left child.
 * 3. Recursively do the same for left and right subtrees.
 *
 *
 * TIME COMPLEXITY
 * ────────────────
 * O(N) — every node is visited once.
 *
 *
 * SPACE COMPLEXITY
 * ─────────────────
 * O(H) — recursion stack.
 *
 */

class Solution {

    public Node connect(Node node) {

        if (node == null) {
            return node;
        }

        // Connect left child → right child
        if (node.left != null) {

            node.left.next = node.right;

            // Connect right child → next parent's left child
            if (node.next != null) {
                node.right.next = node.next.left;
            }
        }

        // Process both subtrees
        connect(node.left);
        connect(node.right);

        return node;
    }
}