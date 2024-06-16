package co.josh.pyson;

/**
 * Exception thrown when PYSON contains invalid formatting
 */
public class InvalidPysonFormatException extends Exception {
    /**
     * Constructs the exception
     * @param message the message shown when the exception is thrown
     * @since 0.1.0
     * @see Exception#Exception(String)
     */
    public InvalidPysonFormatException(String message) {
        super(message);
    }
}
