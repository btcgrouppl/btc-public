package pl.btcgrouppl.btc.backend.commons.cqrs;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 19.05.15.
 * <p>
 *     Command hander general interface. It handles operations for specific command
 * </p>
 * @param <T> type of command to handle
 * @param <R> result type for command (important only in case of synchronous commands). Void in case of async
 */
public interface CommandHandler<T, R> {

    R handleCommand(T command);
}
