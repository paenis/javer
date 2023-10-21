package cse214.hw1;

/**
 * Exception thrown when a person with the same details already exists.
 */
public class PersonAlreadyExistsException extends Exception {
    /**
     * Constructs a new PersonAlreadyExistsException.
     */
    public PersonAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new PersonAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PersonAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersonAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public PersonAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
