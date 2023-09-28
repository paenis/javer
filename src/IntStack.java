import java.util.LinkedList;

public class IntStack {
    LinkedList<Integer> stack;

    public IntStack() {
        stack = new LinkedList<>();
    }

    public void push(int i) {
        stack.addFirst(i);
    }

    public int pop() {
        return stack.removeFirst();
    }
}
