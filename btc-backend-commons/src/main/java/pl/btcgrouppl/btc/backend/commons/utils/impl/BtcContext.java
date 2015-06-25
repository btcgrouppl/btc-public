package pl.btcgrouppl.btc.backend.commons.utils.impl;

import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.utils.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     Global BTC context. Global utilities for all classes, such as excecutor services etc.
 * </p>
 */
@Component
public enum BtcContext implements Context {

    /**
     * Instance with cached thread pool by default
     */
    INSTANCE(Executors.newCachedThreadPool());

    private ExecutorService globalExecutorService;

    private BtcContext(ExecutorService executorService) {
        this.globalExecutorService = executorService;
    }

    @Override
    public ExecutorService getGlobalExecutor() {
        return globalExecutorService;
    }
}
