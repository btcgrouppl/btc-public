package pl.btcgrouppl.btc.backend.commons.ddd.impl;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import pl.btcgrouppl.btc.backend.commons.ddd.EventPublisher;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 */
public class DefaultEventPublisher implements EventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DefaultEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(Object event) {
    }
}
