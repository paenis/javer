package moe.cark.hw1;

/**
 * Exception thrown when a person does not exist.
 */
public class PersonDoesNotExistException extends Exception {
    /**
     * Constructs a new PersonDoesNotExistException.
     */
    public PersonDoesNotExistException() {
        super();
    }

    /**
     * Constructs a new PersonDoesNotExistException with the specified detail message.
     *
     * @param message the detail message of the exception
     */
    public PersonDoesNotExistException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersonDoesNotExistException with the specified detail message and cause.
     *
     * @param message the detail message of the exception
     * @param cause   the cause of the exception
     */
    public PersonDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
