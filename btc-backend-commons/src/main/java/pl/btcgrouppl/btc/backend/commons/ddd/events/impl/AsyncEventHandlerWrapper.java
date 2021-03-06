package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.ddd.events.AsyncEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.utils.impl.BtcContext;

import java.util.concurrent.ExecutorService;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Async wrapper for another event handler instances.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class AsyncEventHandlerWrapper implements AsyncEventHandler, ConditionalEventHandler {

    private static final Logger LOG = LogManager.getLogger(AsyncEventHandlerWrapper.class);

    @NonNull
    protected EventHandler wrappedEventHandler;

    @Override
    public void handle(final Object event) {
        ExecutorService globalExecutor = BtcContext.INSTANCE.getGlobalExecutor();
        globalExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.debug("Trying to invoke wrapped event handler");
                    wrappedEventHandler.handle(event);
                    onSuccessHandle(event);
                }
                catch(EventExecutionException e) {
                    LOG.debug("Error on invoking wrapped event handler. Moving to onFailure()", e);
                    onFailureHandle(e);
                }
            }
        });
    }

    /**
     * Override this method when you want to customize onSuccess behaviour. By default, empty method.
     * @param result typically, dispatched event passed here
     */
    @Override
    public void onSuccessHandle(Object result) {
    }

    /**
     * Override this method when you want to customize onFailure behaviour. By default, empty method.
     * @param e
     */
    @Override
    public void onFailureHandle(Exception e) {
    }

    @Override
    public boolean isEventApplicable(Object event) {
        return (wrappedEventHandler instanceof ConditionalEventHandler)
                ? ((ConditionalEventHandler)wrappedEventHandler).isEventApplicable(event)
                : true;
    }
}