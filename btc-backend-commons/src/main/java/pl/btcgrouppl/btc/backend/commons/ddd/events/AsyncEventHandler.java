package pl.btcgrouppl.btc.backend.commons.ddd.events;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Async event handler interface
 * </p>
 */
public interface AsyncEventHandler extends EventHandler {

    /**
     * Passing exception here
     * @param e
     */
    void onFailure(Exception e);
}
