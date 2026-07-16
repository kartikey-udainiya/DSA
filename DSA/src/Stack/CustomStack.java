package Stack;

public class CustomStack {

    protected int[] data;
    private static final int DEFAULT_SIZE = 10;
    protected int ptr = -1;

    // Default constructor
    public CustomStack() {
        this(DEFAULT_SIZE);
    }

    // Constructor with custom size
    public CustomStack(int size) {
        data = new int[size];
    }

    // Push
    public boolean push(int item) {

        if (isFull()) {
            System.out.println("Stack is full");
            return false;
        }

        ptr++;
        data[ptr] = item;
        return true;
    }

    // Pop
    public int pop() throws Exception {

        if (isEmpty()) {
            throw new Exception("Cannot pop from an empty stack");
        }

        return data[ptr--];
    }

    // Peek
    public int peek() throws Exception {

        if (isEmpty()) {
            throw new Exception("Stack is empty");
        }

        return data[ptr];
    }

    // Check if stack is full
    public boolean isFull() {
        return ptr == data.length - 1;
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return ptr == -1;
    }

    // Size of stack
    public int size() {
        return ptr + 1;
    }

    // Display stack
    public void display() {

        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }

        System.out.println("Top");
        System.out.println(" | ");

        for (int i = ptr; i >= 0; i--) {
            System.out.println(data[i]);
        }

        System.out.println("---");
    }
}
