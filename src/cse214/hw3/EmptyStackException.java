package cse214.hw3;

public class EmptyStackException extends Exception {
    public EmptyStackException(String message) {
        super(message);
    }

    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
}
