/*
Node Class that has both int data and IntNode next
 */
public class IntNode {
    private int data;
    private IntNode next;

    /*
    Constructor: sets next to initial state null, and data to the inputted parameter value
     */
    public IntNode(int data) {
        this.data = data;
        next = null;
    }

    /*
    Getters and Setters for data and next
     */
    public int getData() {
        return data;
    }

    public void setData(int setData) {
        data = setData;
    }

    public IntNode getNext() {
        return next;
    }

    public void setNext(IntNode setNext) {
        next = setNext;
    }

}
