package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;

import java.util.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 */
@Component
@Qualifier("defaultEventPublisher")
public class DefaultEventPublisher implements EventPublisher {

    private static final Logger LOG = LogManager.getLogger(DefaultEventPublisher.class);

    protected Set<EventHandler> eventHandlers = Collections.synchronizedSet(new TreeSet<EventHandler>());

    @Override
    public void publish(@NonNull Object event) {
        LOG.debug("Publishing event: " + event.toString() + ". Checking conditions and passing to EventHandlers.");
        for(EventHandler item: getHandlers()) {
            item.handle(event);
        }
    }

    @Override
    public Set<EventHandler> getHandlers() {
        return Collections.unmodifiableSet(eventHandlers);
    }

    @Override
    public void addHandler(@NonNull EventHandler eventHandler) {
        eventHandlers.add(eventHandler);
    }
}