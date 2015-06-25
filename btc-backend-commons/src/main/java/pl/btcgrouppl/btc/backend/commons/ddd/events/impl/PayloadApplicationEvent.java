package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Waiting for spring context > 4.1 in rel spring boot, to introduce PayloadApplicationEvent OOTB
 * </p>
 */
public class PayloadApplicationEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public PayloadApplicationEvent(@NonNull Object source) {
        super(source);
    }
}
