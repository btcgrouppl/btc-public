package pl.btcgrouppl.btc.backend.commons.ddd.events;

import java.util.List;
import java.util.Set;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     EventPublisher interface. Used to publishing events
 * </p>
 */
public interface EventPublisher {

    void publish(Object event);

    Set<EventHandler> getHandlers();

    void addHandler(EventHandler eventHandler);
}