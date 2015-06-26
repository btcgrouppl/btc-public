package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandlerAware;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.utils.models.exceptions.SpElParseException;

import java.util.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 */
@Component
@Qualifier("defaultEventPublisher")
public class DefaultEventPublisher implements EventPublisher, ConditionalEventHandlerAware {

    private static final Logger LOG = LogManager.getLogger(DefaultEventPublisher.class);

    protected Set<EventHandler> eventHandlers = Collections.synchronizedSet(new TreeSet<EventHandler>());

    @Override
    public void publish(@NonNull final Object event) {
        LOG.debug("Publishing event: " + event.toString() + ". Checking conditions and passing to EventHandlers.");
        for(EventHandler item: getHandlers()) {
            if(!isHandlerApplicable(item, event)) {
                LOG.debug("Event: " + event + " NOT applicable for handler: " + item + ".");
                continue;
            }
            LOG.debug("Event: " + event + " applicable for handler: " + item + ".");
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

    @Override
    public boolean isHandlerApplicable(Object eventHandler, Object event) {
        try {
            return (eventHandler==null || (!(eventHandler instanceof ConditionalEventHandler)))
                    ? true
                    : ((ConditionalEventHandler)eventHandler).isEventApplicable(event);
        }
        catch(SpElParseException e) {
            LOG.error("Spring Expression Language (SpEL) error while checking if handler "+ eventHandler + " is applicable to event: " + event, e);
            return false;
        }
    }
}