package pl.btcgrouppl.btc.backend.commons.ddd.events;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 *     Conditional event handler checks, if {@link pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventConditionAnnotation} is present, <br />
 *     and, if so, checks, if event object is applicable
 * </p>
 */
public interface ConditionalEventHandler {

    /**
     *
     * @param event
     * @return boolean
     */
    boolean isEventApplicable(Object event);
}
