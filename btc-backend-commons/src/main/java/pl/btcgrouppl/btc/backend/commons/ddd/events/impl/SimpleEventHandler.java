package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Simple type of event handler.
 * </p>
 */
@Getter
public class SimpleEventHandler implements EventHandler {

    private static final Logger LOG = LogManager.getLogger(SimpleEventHandler.class);


    /**
     * Java reflect method to be wrapped
     */
    @NonNull
    private Method wrappedMethod;

    @NonNull
    private Object instance;

    @Override
    public void handle(Object event) {
        try {
            LOG.debug("Trying to invoke event handler for method: " + wrappedMethod.toString() + " of object: " + instance.toString());
            wrappedMethod.invoke(instance, wrappedMethod);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("Error during event handler method invocation: " + wrappedMethod.toString() + " of object: " + instance.toString() + ". Passing EventExecutionException.", e);
            throw EventExecutionException.create(e, EventExecutionException.TYPE.METHOD_INVOKE_FAIL);
        }
    }
}
