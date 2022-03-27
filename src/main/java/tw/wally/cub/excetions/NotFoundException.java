package tw.wally.cub.excetions;

/**
 * @author - wally55077@gmail.com
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(Object id) {
        super(String.format("Resource not found: %s", id));
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
