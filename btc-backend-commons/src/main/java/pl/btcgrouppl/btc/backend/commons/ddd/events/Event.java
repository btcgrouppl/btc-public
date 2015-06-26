package pl.btcgrouppl.btc.backend.commons.ddd.events;

import java.util.UUID;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 *     Event interface. You should implement it at every event object
 * </p>
 */
public interface Event {

    /**
     * Getting unique ID for event
     * @return UUID
     */
    UUID getId();
}
