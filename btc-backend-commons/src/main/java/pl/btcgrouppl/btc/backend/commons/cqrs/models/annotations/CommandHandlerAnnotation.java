package pl.btcgrouppl.btc.backend.commons.cqrs.models.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 19.05.15.
 * <p>Command handler annotation</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface CommandHandlerAnnotation {
}
