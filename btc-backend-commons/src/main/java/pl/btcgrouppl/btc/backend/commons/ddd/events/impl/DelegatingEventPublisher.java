package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;

import java.util.List;
import java.util.Set;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     Delegating event publisher. Using a set of delegating event publisher in specific order. Created manually in
 *     {@link pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration}
 * </p>
 */
@RequiredArgsConstructor
public class DelegatingEventPublisher implements EventPublisher {

    private static final Logger LOG = LogManager.getLogger(DelegatingEventPublisher.class);

    @NonNull
    private List<EventPublisher> eventPublisherList;

    @Override
    public void publish(Object event) {
        for(EventPublisher item: eventPublisherList) {
            item.publish(event);
        }
    }

    @Override
    public Set<EventHandler> getHandlers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addHandler(EventHandler eventHandler) {
        for(EventPublisher item: eventPublisherList) {
            try {
                item.addHandler(eventHandler);
            }
            catch(UnsupportedOperationException e) {
                LOG.error("Error while adding event handler for publisher.", e);
            }
        }
    }
}
