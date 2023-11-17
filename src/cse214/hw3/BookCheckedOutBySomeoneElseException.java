package cse214.hw3;

public class BookCheckedOutBySomeoneElseException extends Exception {
    public BookCheckedOutBySomeoneElseException(String message) {
        super(message);
    }

    public BookCheckedOutBySomeoneElseException(String message, Throwable cause) {
        super(message, cause);
    }
}
