package cse214.hw3;

public class InvalidReturnDateException extends Exception {
    public InvalidReturnDateException(String message) {
        super(message);
    }

    public InvalidReturnDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
