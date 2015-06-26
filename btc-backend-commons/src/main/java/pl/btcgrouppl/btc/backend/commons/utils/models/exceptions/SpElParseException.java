package pl.btcgrouppl.btc.backend.commons.utils.models.exceptions;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 */
public class SpElParseException extends RuntimeException {

    public SpElParseException() {
    }

    public SpElParseException(String message) {
        super(message);
    }

    public SpElParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpElParseException(Throwable cause) {
        super(cause);
    }
}
