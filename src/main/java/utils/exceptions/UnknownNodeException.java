package utils.exceptions;

public class UnknownNodeException extends Exception {

    public UnknownNodeException(String message) {
        super(message);
    }

    public UnknownNodeException(String message, Exception cause) {
        super(message, cause);
    }
}
