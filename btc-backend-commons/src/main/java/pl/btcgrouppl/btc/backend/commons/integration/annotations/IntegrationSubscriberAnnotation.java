package pl.btcgrouppl.btc.backend.commons.integration.annotations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 06.06.15.
 * <p>
 *     Integration subscriber interface. Used by bean registrar to register beans listening for new messages
 *     from spring integration
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface IntegrationSubscriberAnnotation {

    /**
     * Channel to subscribe. By default, it uses {@link Constants.INTEGRATION#GENERAL_CHANNEL}
     * @return String
     */
    String channel() default Constants.INTEGRATION.GENERAL_CHANNEL;

    /**
     * Method name looked up by post processor to pass message payload. By default, onSubscribe method used
     *
     * <B>NOTICE!!! Not used right now, as of IntegrationSubscriber interface. All classes with this annotation should implement this interface <br />
     * Otherwise, no message will be delivered to object, and IntegrationMessageNotHandledException will be thrown </B>
     * @return String
     */
    String method() default "onSubscribe";

}
