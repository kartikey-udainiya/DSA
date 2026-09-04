1. What is a Heap?

A Heap is a special type of binary tree that follows two rules:

Rule 1: It must be a Complete Binary Tree

A complete binary tree means:

Every level is completely filled except possibly the last.
The last level is filled from left to right.

Example:

        10
       /  \
      20   30
     / \   /
    40 50 60

This is complete.

But:

        10
       /  \
      20   30
       \
        40

is not complete because the left side of the last level isn't filled from left to right.

2. There are two types of Heap
   Max Heap

The parent is always greater than or equal to its children.

        50
       /  \
      30   40
     / \   /
    10 20 35

For every node:

parent >= children

Therefore, the largest element is always at the root.

Min Heap

The parent is always smaller than or equal to its children.

        10
       /  \
      20   15
     / \   /
    40 30 25

For every node:

parent <= children

Therefore, the smallest element is always at the root.

3. Important: Heap is NOT a BST

This is a very important distinction.

In a BST:

          50
         /  \
       30    70
      / \    / \
    20 40  60 80

You have:

everything left < node < everything right

But in a Max Heap:

        50
       /  \
      30   40
     / \   /
    10 20 35

We only know:

50 > 30
50 > 40
30 > 10
30 > 20
40 > 35

We don't know whether 30 > 35.

So a Heap is not sorted.

4. Why do we use Heap?

The biggest advantage is that we can quickly access the extreme element.

Max Heap
max → root
Min Heap
min → root

Getting it is:

O(1)

And insertion/deletion is:

O(log N)

That's why heaps are heavily used in:

Priority Queue
Heap Sort
Top K problems
Kth largest/smallest
Scheduling
Dijkstra's algorithm
Prim's algorithm
5. The interesting part: Heap is usually stored in an Array

This is where Heap becomes really interesting.

Take this Max Heap:

        50
       /  \
      30   40
     / \   /
    10 20 35

We can store it as:

[50, 30, 40, 10, 20, 35]

We don't need Node objects and left/right references.

Why?

Because a complete binary tree has a very predictable structure.

6. Parent/Child formulas

Suppose an element is at index i.

Parent
parent = (i - 1) / 2
Left child
left = 2 * i + 1
Right child
right = 2 * i + 2

For example:

index:   0   1   2   3   4   5
value:  50  30  40  10  20  35

For 30:

i = 1

Left:

2 * 1 + 1 = 3

→ 10

Right:

2 * 1 + 2 = 4

→ 20

Parent:

(1 - 1) / 2 = 0

→ 50

7. How do we insert into a Heap?

Suppose we have:

        50
       /  \
      30   40
     / \
    10 20

Array:

[50, 30, 40, 10, 20]

Now insert:

60

Because the tree must remain complete, we first put 60 at the next available position:

        50
       /  \
      30   40
     / \   /
    10 20 60

But this violates Max Heap:

60 > 40

So we move 60 upward.

This process is called:

Heapify Up / Sift Up
50
/  \
30   60
/ \   /
10 20 40

Still:

60 > 50

Move again:

        60
       /  \
      30   50
     / \   /
    10 20 40

Now the Max Heap property is restored.

8. What happens when we remove the root?

Suppose:

        60
       /  \
      30   50
     / \   /
    10 20 40

We want to remove the maximum.

The maximum is always the root:

60

But now we need to maintain the complete-tree structure.

So we take the last element:

40

and put it at the root:

        40
       /  \
      30   50
     / \
    10 20

But:

40 < 50

Heap property is broken.

So we move 40 downward.

This is called:

Heapify Down / Sift Down
50
/  \
30   40
/ \
10 20

Heap restored.

9. The two fundamental operations

So remember:

Insertion
Add at end
↓
Heapify Up


Remove root
Replace root with last element
↓
Heapify Down

This is basically the heart of Heap implementation.

10. Java Implementation — Max Heap

Let's implement our own heap rather than using Java's PriorityQueue.

class MaxHeap {

    private int[] arr;
    private int size;

    public MaxHeap(int capacity) {
        arr = new int[capacity];
        size = 0;
    }

    public void insert(int value) {

        if (size == arr.length) {
            throw new RuntimeException("Heap is full");
        }

        arr[size] = value;

        heapifyUp(size);

        size++;
    }

    private void heapifyUp(int index) {

        int parent = (index - 1) / 2;

        if (index > 0 && arr[index] > arr[parent]) {

            swap(index, parent);

            heapifyUp(parent);
        }
    }

    private void swap(int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

But there is a tiny issue with the order of size++.

A cleaner implementation is:

public void insert(int value) {

    if (size == arr.length) {
        throw new RuntimeException("Heap is full");
    }

    arr[size] = value;

    heapifyUp(size);

    size++;
}

Actually, this works because heapifyUp() doesn't need size.

11. Remove maximum
    public int remove() {

    if (size == 0) {
    throw new RuntimeException("Heap is empty");
    }

    int removed = arr[0];

    arr[0] = arr[size - 1];

    size--;

    heapifyDown(0);

    return removed;
    }

Now implement heapifyDown():

private void heapifyDown(int index) {

    int left = 2 * index + 1;
    int right = 2 * index + 2;

    int largest = index;

    if (left < size && arr[left] > arr[largest]) {
        largest = left;
    }

    if (right < size && arr[right] > arr[largest]) {
        largest = right;
    }

    if (largest != index) {

        swap(index, largest);

        heapifyDown(largest);
    }
}

Notice something important:

largest

is used because we need to compare the node with both children.

For a Max Heap, we move toward the larger child.

12. Complete implementation
    class MaxHeap {

    private int[] arr;
    private int size;

    public MaxHeap(int capacity) {
    arr = new int[capacity];
    size = 0;
    }

    public void insert(int value) {

        if (size == arr.length) {
            throw new RuntimeException("Heap is full");
        }

        arr[size] = value;

        heapifyUp(size);

        size++;
    }

    private void heapifyUp(int index) {

        int parent = (index - 1) / 2;

        if (index > 0 && arr[index] > arr[parent]) {

            swap(index, parent);

            heapifyUp(parent);
        }
    }

    public int remove() {

        if (size == 0) {
            throw new RuntimeException("Heap is empty");
        }

        int removed = arr[0];

        arr[0] = arr[size - 1];

        size--;

        heapifyDown(0);

        return removed;
    }

    private void heapifyDown(int index) {

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        int largest = index;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != index) {

            swap(index, largest);

            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    }

You could test it:

MaxHeap heap = new MaxHeap(10);

heap.insert(10);
heap.insert(20);
heap.insert(5);
heap.insert(30);
heap.insert(15);

System.out.println(heap.remove()); // 30
System.out.println(heap.remove()); // 20
System.out.println(heap.remove()); // 15
13. Complexity
    Operation	Complexity
    Get max/min	O(1)
    Insert	O(log N)
    Remove root	O(log N)
    Search arbitrary value	O(N)

The reason insertion/removal is O(log N) is that we only travel up or down the height of the tree.

And because a heap is complete:

height ≈ log₂(N)
The mental picture I want you to remember

Don't try to memorize the entire implementation yet.

Just remember this:

                 HEAP
                   |
          Complete Binary Tree
                   +
             Heap Property
                   |
        ┌──────────┴──────────┐
        ↓                     ↓
     INSERT                REMOVE
        ↓                     ↓
Add at END          Remove ROOT
↓                     ↓
Heapify UP           Last → ROOT
↓
Heapify DOWN

And the array formulas:

parent = (i - 1) / 2

left   = 2*i + 1

right  = 2*i + 2

Once these are clear, the Java implementation becomes much easier to understand.