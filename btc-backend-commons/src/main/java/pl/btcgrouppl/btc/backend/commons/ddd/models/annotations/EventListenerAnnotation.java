package pl.btcgrouppl.btc.backend.commons.ddd.models.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Event listener annotation. Checking, if event listener is async (default), or not, and passing it to speficic wrapper<br />
 *     <b>Prepared to compatibility with spring ver < 4.2.</b> Waiting to new release of spring boot with spring core ver 4.2.1. It
 *     introduces EventListener annotation and PayloadApplicationEvent
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListenerAnnotation {

    /**
     * Is true, passed to EventHandlerAsyncWrapper, on the other hand to DefaultEventHandler
     * @return boolean
     */
    boolean isAsync() default true;
}
