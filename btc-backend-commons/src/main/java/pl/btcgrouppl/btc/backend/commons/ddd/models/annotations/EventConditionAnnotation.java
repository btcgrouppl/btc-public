package pl.btcgrouppl.btc.backend.commons.ddd.models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 *     Event conditional annotation. Annotation can be used both on Event class and listener method to indicate, if listener
 *     can consume given Event. Using Spring expression language (SpEL)
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EventConditionAnnotation {

    /**
     * SpEL expression to be checked by ExpressionParser
     * @return String
     */
    String expression() default "";
}
