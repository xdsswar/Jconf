package xss.it.conf;

/**
 * @author XDSSWAR
 * Created on 05/16/2023
 */
public class NullSecretKeyException extends RuntimeException{
    /**
     * Constructs a {@code NullSecretKeyException} with a default error message.
     */
    public NullSecretKeyException() {
        super("Secret key is null or empty.");
    }

    /**
     * Constructs a {@code NullSecretKeyException} with the specified error message.
     *
     * @param message the detail message
     */
    public NullSecretKeyException(String message) {
        super(message);
    }


    /**
     * Constructs a {@code NullSecretKeyException} with the specified error message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public NullSecretKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code NullSecretKeyException} with the specified cause and a default error message.
     *
     * @param cause the cause
     */
    public NullSecretKeyException(Throwable cause) {
        super("Secret key is null or empty.", cause);
    }
}
