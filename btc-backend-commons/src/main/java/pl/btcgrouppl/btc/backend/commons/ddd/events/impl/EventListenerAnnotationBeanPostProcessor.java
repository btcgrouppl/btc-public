package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;

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

    private EventPublisher delegatingEventPublisher;

    @Autowired
    public EventListenerAnnotationBeanPostProcessor(EventPublisher delegatingEventPublisher) {
        this.delegatingEventPublisher = delegatingEventPublisher;
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
                EventHandler handler = new SimpleEventHandler(method, o);
                if(annotation.isAsync()) {
                    handler = new AsyncEventHandlerWrapper(handler);
                }
                delegatingEventPublisher.addHandler(handler);
            }
        }
        return o;
    }
}
