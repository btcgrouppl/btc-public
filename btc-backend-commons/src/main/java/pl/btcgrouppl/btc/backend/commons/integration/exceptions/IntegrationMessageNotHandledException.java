package pl.btcgrouppl.btc.backend.commons.integration.exceptions;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 06.06.15.
 * <p>
 *     Exception thrown when integration message were not passed to subscriber properly.
 * </p>
 */
public class IntegrationMessageNotHandledException extends RuntimeException {

    public IntegrationMessageNotHandledException() {
    }

    public IntegrationMessageNotHandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationMessageNotHandledException(String message) {
        super(message);
    }

    public IntegrationMessageNotHandledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IntegrationMessageNotHandledException(Throwable cause) {
        super(cause);
    }
}
