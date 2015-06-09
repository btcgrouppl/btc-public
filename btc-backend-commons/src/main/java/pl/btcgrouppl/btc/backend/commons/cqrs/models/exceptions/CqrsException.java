package pl.btcgrouppl.btc.backend.commons.cqrs.exceptions;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 */
public class CqrsException extends RuntimeException {

    public CqrsException() {
    }

    public CqrsException(String message) {
        super(message);
    }

    public CqrsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CqrsException(Throwable cause) {
        super(cause);
    }

    public CqrsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
