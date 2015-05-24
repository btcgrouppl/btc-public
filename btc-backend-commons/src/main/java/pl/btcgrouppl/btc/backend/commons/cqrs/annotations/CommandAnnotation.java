package pl.btcgrouppl.btc.backend.commons.cqrs.annotations;

import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 19.05.15.
 * <p>Command annotation</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation {

    /**
     * By default, all commands are asynchronous
     * @return boolean
     */
    boolean isAsync() default true;

}
