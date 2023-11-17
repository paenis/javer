package cse214.hw3;

public class BookAlreadyCheckedOutException extends Exception {
    public BookAlreadyCheckedOutException(String message) {
        super(message);
    }
}
