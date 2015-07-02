package pl.btcgrouppl.btc.backend.commons.ddd.events;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Async event handler interface
 * </p>
 */
public interface AsyncEventHandler extends EventHandler {

    /**
     * On success callback. Executed in case of success.
     * @param result typically, dispatched event passed here
     */
    void onSuccessHandle(Object result);

    /**
     * Passing exception here
     * @param e
     */
    void onFailureHandle(Exception e);
}
