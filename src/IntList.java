public class IntList {
    private IntNode head;
    private IntNode tail;
    private IntNode cursor;

    /*
     * Initializes an IntList object
     */
    public IntList() {
        head = tail = cursor = null;
    }

    public static void main(String... args) {
        IntList myList = new IntList();


    }

    /*
     * Adds a new node which will be a head to the IntList.
     * Cursor is set to head.
     * If the list was empty, tail is set to head.
     *
     */
    public void addNewHead(int element) {
        IntNode newNode = new IntNode(element);

        newNode.setNext(head);
        head = newNode;

        if (tail == null)
            tail = head;

        cursor = head;
    }

    /*
     * Adds a new IntNode after the current position
     * of the cursor in the IntList. The cursor is then
     * set equal to the newNode.
     */
    public void addIntAfter(int element) {
        IntNode newNode = new IntNode(element);

        if (cursor == null) {
            head = newNode;
            tail = newNode;
            cursor = newNode;
        } else {
            newNode.setNext(cursor.getNext());
            cursor.setNext(newNode);
            cursor = newNode;
            if (cursor.getNext() == null) {
                tail = cursor;
            }
        }
    }

    /*
     * Removes the element after the cursor, if it exists.
     * In the case the cursor becomes the last element of the list,
     * tail is set to be the cursor.
     */
    public void removeIntAfter() {
        if (cursor != tail) {
            cursor.setNext(cursor.getNext().getNext());

            if (cursor.getNext() == null) {
                tail = cursor;
            }
        }
    }

    /*
     * Returns head node.
     */
    public IntNode getHead() {
        return head;
    }

    /*
     * Removes the first occurrence of a IntNode with the data
     * target if it exists. If it doesn't exist, return null.
     */
    public boolean remove(int target) {
        IntNode nodePtr = head;
        IntNode prevPtr = null;

        while (nodePtr != null && nodePtr.getData() != target) {
            prevPtr = nodePtr;
            nodePtr = nodePtr.getNext();
        }

        if (nodePtr != null)
            prevPtr.setNext(nodePtr.getNext());

        return nodePtr != null;
    }

    public void removeHead() {
        if (head != null) {
            head = head.getNext();
        }

        if (head == null)
            tail = null;

        cursor = head;
    }

    public boolean advanceCursor() {
        if (cursor != tail) {
            cursor = cursor.getNext();
            return true;
        }

        return false;
    }

    public int getNodeData() {
        if (cursor == null)
            throw new IllegalArgumentException("List is empty!");

        return cursor.getData();
    }

    public void resetCursor() {
        cursor = head;
    }

    public boolean isEmpty() {
        return cursor == null;
    }

    public int listLength() {
        IntNode nodePtr = head;
        int ans = 0;
        while (nodePtr != null) {
            ans++;
            nodePtr = nodePtr.getNext();
        }

        return ans;
    }

    /*
     * Searches for an element in the list.
     * If it is in the list, the cursor is set to that element,
     * and true is returned.
     */
    public boolean listSearchCursor(int target) {
        IntNode nodePtr = head;
        while (nodePtr != null) {
            if (target == nodePtr.getData()) {
                cursor = nodePtr;
                return true;
            }
            nodePtr = nodePtr.getNext();
        }
        return false;
    }

    public IntNode listSearchNode(int target) {
        IntNode nodePtr = head;
        while (nodePtr != null) {
            if (target == nodePtr.getData())
                return nodePtr;

            nodePtr = nodePtr.getNext();
        }
        return null;
    }

    public boolean listPosition(int position) {
        if (position <= 0)
            throw new IllegalArgumentException("Pos <= 0");

        IntNode nodePtr = head;
        int i = 1;

        while (i < position && nodePtr != null) {
            nodePtr = nodePtr.getNext();
            i++;
        }

        if (nodePtr != null)
            cursor = nodePtr;
        return (nodePtr != null);
    }

    /*
    /*
     * Prints all data in the list.
     */
    public void printList() {
        IntNode nodePtr = head;

        while (nodePtr != null) {
            System.out.print(nodePtr.getData() + " ");
            nodePtr = nodePtr.getNext();
        }
        System.out.println();
    }

}
