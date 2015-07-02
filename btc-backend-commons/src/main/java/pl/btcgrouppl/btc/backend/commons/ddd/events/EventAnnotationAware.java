package pl.btcgrouppl.btc.backend.commons.ddd.events;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 02.07.15.
 * <p>
 *     Dedicated for classes which want to check EventAnnotation on object
 * </p>
 */
public interface EventAnnotationAware {

    boolean isEventExternal(Object event);
}
