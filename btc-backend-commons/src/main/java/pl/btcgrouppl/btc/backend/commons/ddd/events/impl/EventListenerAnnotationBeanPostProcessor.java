package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.lang.reflect.Method;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Bean post processor detects all methods with {@link pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation},
 *     and add proper event handler
 * </p>
 */
@Component
public class EventListenerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final EventPublisher delegatingEventPublisher;
    private final SpElParserUtil spElParserUtil;

    @Autowired
    public EventListenerAnnotationBeanPostProcessor(@Qualifier(Constants.QUALIFIERS.DELEGATING_PUBLISHER) EventPublisher delegatingEventPublisher,
                                                    @Qualifier(Constants.QUALIFIERS.SPEL_PARSER) SpElParserUtil spElParserUtil) {
        this.delegatingEventPublisher = delegatingEventPublisher;
        this.spElParserUtil = spElParserUtil;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Method[] publicMethods = o.getClass().getMethods();   //We accept only public methods
        for(Method method: publicMethods) {
            if(method.isAnnotationPresent(EventListenerAnnotation.class)) {
                EventListenerAnnotation annotation = method.getAnnotation(EventListenerAnnotation.class);
                EventHandler handler = SimpleEventHandler.builder()
                        .instance(o)
                        .spElParserUtil(spElParserUtil)
                        .wrappedMethod(method).build();
                if(annotation.isAsync()) {
                    handler = new AsyncEventHandlerWrapper(handler);
                }
                delegatingEventPublisher.addHandler(handler);
            }
        }
        return o;
    }
}
