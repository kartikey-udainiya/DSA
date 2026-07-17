package Queue;

import java.util.Stack;

// implemeting Queue using stack

public class LC232 {

    Stack<Integer> input, output;
    LC232() {
        input = new Stack<>();
        output = new Stack<>();
    }
    public void push(int x) {
        input.push(x);
    }
    public int pop() {
        move();
        return output.pop();
    }
    public int peek() {
        move();
        return output.peek();
    }
    public void move(){
        if(output.isEmpty()){
            while(!input.isEmpty()){
                output.push(input.pop());
            }

        }
    }
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }

}
