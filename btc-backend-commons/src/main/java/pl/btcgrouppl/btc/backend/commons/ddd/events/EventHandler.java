package pl.btcgrouppl.btc.backend.commons.ddd.events;

import rx.Observable;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Event handler interface
 * </p>
 */
public interface EventHandler {

    /**
     * If we expect some response from handler,
     * @param event
     */
    void handle(Object event);
}
