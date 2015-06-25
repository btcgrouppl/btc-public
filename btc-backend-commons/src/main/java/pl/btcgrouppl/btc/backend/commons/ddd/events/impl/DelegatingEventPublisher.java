package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;

import java.util.List;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     Delegating event publisher. Using a set of delegating event publisher in specific order. Created manually in
 *     {@link pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration}
 * </p>
 */
@RequiredArgsConstructor
public class DelegatingEventPublisher implements EventPublisher {

    @NonNull
    private List<EventPublisher> eventPublisherList;

    @Override
    public void publish(Object event) {
        for(EventPublisher item: eventPublisherList) {
            item.publish(event);    //TODO some conditions would be awesome --> ContitionalEvent interface and ConditionalEventAware interface
        }
    }
}
