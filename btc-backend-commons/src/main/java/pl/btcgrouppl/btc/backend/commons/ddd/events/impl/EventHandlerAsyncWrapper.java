package pl.btcgrouppl.btc.backend.commons.ddd.events.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.events.AsyncEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.utils.impl.BtcContext;
import rx.subjects.AsyncSubject;
import rx.subjects.PublishSubject;

import java.util.concurrent.ExecutorService;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Async wrapper for another event handler instances.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class EventHandlerAsyncWrapper implements AsyncEventHandler {

    private static final Logger LOG = LogManager.getLogger(EventHandlerAsyncWrapper.class);

    @NonNull
    private EventHandler wrappedEventHandler;

    @Override
    public void handle(final Object event) {
        ExecutorService globalExecutor = BtcContext.INSTANCE.getGlobalExecutor();
        globalExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.debug("Trying to invoke wrapped event handler");
                    wrappedEventHandler.handle(event);
                }
                catch(EventExecutionException e) {
                    LOG.debug("Error on invoking wrapped event handler. Moving to onFailure()", e);
                    onFailure(e);
                }
            }
        });
    }

    /**
     * Override this method when you want to customize onFailure behaviour. By default, empty method.
     * @param e
     */
    @Override
    public void onFailure(Exception e) {
    }
}
