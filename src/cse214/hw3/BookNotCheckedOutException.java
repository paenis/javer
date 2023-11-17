package cse214.hw3;

public class BookNotCheckedOutException extends Exception {
    public BookNotCheckedOutException(String message) {
        super(message);
    }

    public BookNotCheckedOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
