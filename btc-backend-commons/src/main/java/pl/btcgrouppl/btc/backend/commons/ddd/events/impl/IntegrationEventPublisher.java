package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventAnnotationAware;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationCommonSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.factories.IntegrationMessageFactory;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

import java.util.Set;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     Publishing messages into general message channel
 * </p>
 */
@Component
@Qualifier("integrationEventPublisher")
public class IntegrationEventPublisher implements EventPublisher, EventAnnotationAware {

    private static final Logger LOG = LogManager.getLogger(IntegrationEventPublisher.class);

    @Autowired
    private IntegrationCommonSpringConfiguration.GeneralMessageGateway generalMessageGateway;


    @Override
    public void publish(Object event) {
        if(!isEventExternal(event)) {
            LOG.debug("Publishing message via spring integration general channel REJECTED!. Event: " + event + ". Event is not external!");
            return;
        }
        try {
            LOG.debug("Publishing message via spring integration general channel. Event: " + event);
            IntegrationMessage integrationMessage = IntegrationMessageFactory.create(event);
            generalMessageGateway.sendMessage(integrationMessage);
        }
        catch(NullPointerException e) {
            LOG.error("Error while creating integration message for event: " + event, e);
        }
    }

    @Override
    public Set<EventHandler> getHandlers() {
        throw new UnsupportedOperationException("Integration event publisher uses spring integration flows!");
    }

    @Override
    public void addHandler(EventHandler eventHandler) {
        throw new UnsupportedOperationException("Integration event publisher uses spring integration flows!");
    }

    @Override
    public void removeHandler(EventHandler eventHandler) {
        throw new UnsupportedOperationException("Integration event publisher uses spring integration flows!");
    }

    @Override
    public boolean isEventExternal(Object event) {
        EventAnnotation eventAnnotation = event.getClass().getAnnotation(EventAnnotation.class);
        if(eventAnnotation!=null) {
            return eventAnnotation.isExternal();
        }
        return false;
    }
}