package pl.btcgrouppl.btc.backend.commons.ddd.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.EventPublisher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     Delegating event publisher. Using a set of delegating event publisher in specific order
 * </p>
 */
@Component
@RequiredArgsConstructor
public class DelegatingEventPublisher implements EventPublisher {

    @NonNull
    private List<EventPublisher> eventPublisherList;

    @Override
    public void publish(Object event) {

    }
}
