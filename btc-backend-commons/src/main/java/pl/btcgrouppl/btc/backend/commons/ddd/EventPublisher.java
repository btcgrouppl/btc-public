package pl.btcgrouppl.btc.backend.commons.ddd;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     EventPublisher interface. Used to publishing events
 * </p>
 */
public interface EventPublisher {

    void publish(Object event);
}