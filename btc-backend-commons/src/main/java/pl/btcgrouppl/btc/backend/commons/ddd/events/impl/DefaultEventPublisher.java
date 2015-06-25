package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 */
@Component
public class DefaultEventPublisher implements EventPublisher {

    private static final Logger LOG = LogManager.getLogger(DefaultEventPublisher.class);

    @NonNull
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DefaultEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(@NonNull Object event) {
        LOG.debug("Publishing event: " + event.toString() + ". Wrapping into PayloadApplicationEvent");
        applicationEventPublisher.publishEvent(new PayloadApplicationEvent(event));
    }
}