package pl.btcgrouppl.btc.backend.commons.ddd.events;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 */
public interface ConditionalEventHandlerAware {

    /**
     * Checking, if handler is applicable
     * @return boolean
     */
    boolean isHandlerApplicable(Object eventHandler, Object event);

}
