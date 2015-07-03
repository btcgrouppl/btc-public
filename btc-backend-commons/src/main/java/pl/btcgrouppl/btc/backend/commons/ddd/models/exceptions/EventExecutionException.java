package pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions;

import lombok.Data;
import lombok.NonNull;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     All exceptions connected with ddd events processing. Create by static method factories:<br />
 *     {@link #create(String, Throwable, TYPE)}<br />
 *     {@link #create(String, TYPE)}<br />
 *     {@link #create(Throwable, TYPE)}
 * </p>
 */
@Data
public class EventExecutionException extends RuntimeException {

    /**
     * Type of event
     */
    public enum TYPE {
        METHOD_INVOKE_FAIL,
        EVENT_DISPATCH_FAIL,
        LISTENER_WRONG_PASS_CONDITION
    }

    private TYPE type;

    private EventExecutionException(String message, TYPE type) {
        super(message);
        this.type = type;
    }

    private EventExecutionException(String message, Throwable cause, TYPE type) {
        super(message, cause);
        this.type = type;
    }

    private EventExecutionException(Throwable cause, TYPE type) {
        super(cause);
        this.type = type;
    }

    public static EventExecutionException create(@NonNull String message, @NonNull TYPE type) {
        return new EventExecutionException(message, type);
    }

    public static EventExecutionException create(@NonNull String message, @NonNull Throwable cause, @NonNull TYPE type) {
        return new EventExecutionException(message, cause, type);
    }

    public static EventExecutionException create(@NonNull Throwable cause, @NonNull TYPE type) {
        return new EventExecutionException(cause, type);
    }

    @Override
    public String getMessage() {
        return "EventExecutionException:::" + getType() + ". Error during event execution (dispatch or passing to subscriber): " + super.getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
