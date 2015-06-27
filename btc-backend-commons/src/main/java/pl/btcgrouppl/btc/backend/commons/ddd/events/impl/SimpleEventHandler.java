package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import com.google.common.base.Strings;
import lombok.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.InternalParseException;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventConditionAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import pl.btcgrouppl.btc.backend.commons.utils.impl.SpElParserUtilImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 * Simple type of event handler.
 * </p>
 */
@Getter
@RequiredArgsConstructor
@Builder
public class SimpleEventHandler implements EventHandler, ConditionalEventHandler {

    private static final Logger LOG = LogManager.getLogger(SimpleEventHandler.class);


    /**
     * Java reflect method to be wrapped
     */
    @NonNull
    private Method wrappedMethod;

    @NonNull
    private Object instance;

    @NonNull
    private SpElParserUtil spElParserUtil;

    @Override
    public void handle(Object event) {
        try {
            LOG.debug("Trying to invoke event handler for method: " + wrappedMethod.toString() + " of object: " + instance.toString());
            wrappedMethod.invoke(instance, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("Error during event handler method invocation: " + wrappedMethod.toString() + " of object: " + instance.toString() + ". Passing EventExecutionException.", e);
            throw EventExecutionException.create(e, EventExecutionException.TYPE.METHOD_INVOKE_FAIL);
        }
    }

    @Override
    public boolean isEventApplicable(Object event) {
        Method wrappedMethod = getWrappedMethod();
        EventConditionAnnotation annotation = wrappedMethod.getAnnotation(EventConditionAnnotation.class);
        if (annotation == null || Strings.isNullOrEmpty(annotation.expression()))
            return true;
        Map<String, Object> argMap = new HashMap<>();
        argMap.put(SpElParserUtil.SPEL_EVENT_ARG_NAME, event);
        return spElParserUtil.parseExpression(annotation.expression(), argMap, Boolean.class);
    }
}
