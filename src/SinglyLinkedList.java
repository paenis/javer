public class SinglyLinkedList {
    class IntNode {
        private int data;
        private IntNode next;

        public IntNode(int data) {
            this.data = data;
            this.next = null;
        }

        public IntNode(int data, IntNode next) {
            this.data = data;
            this.next = next;
        }

        public void link(IntNode next) {
            this.next = next;
        }

        public void unlink() {
            this.next = null;
        }

        public IntNode next() {
            return next;
        }

        public String toString() {
            return String.format("Node(%d)", data);
        }
    }

    IntNode head;
    IntNode tail;
    IntNode cursor;

    public SinglyLinkedList() {
        head = null;
    }

    public void add(int data) {
        if (head == null) {
            head = new IntNode(data);
        } else {
            IntNode curr = head;
            while (curr.next() != null) curr = curr.next();
            curr.link(new IntNode(data));
        }
    }

    public void add(int data, int index) {
        if (index < 0) throw new IndexOutOfBoundsException("index < 0");
        if (index == 0) {
            head = new IntNode(data, head);
        } else {
            IntNode curr = head;
            for (int i = 0; i < index - 1; i++) {
                if (curr == null) throw new IndexOutOfBoundsException("index >= size");
                curr = curr.next();
            }
            curr.link(new IntNode(data, curr.next()));
        }
    }
}
