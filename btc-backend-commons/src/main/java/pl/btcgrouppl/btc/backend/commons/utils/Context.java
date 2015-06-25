package pl.btcgrouppl.btc.backend.commons.utils;

import java.util.concurrent.ExecutorService;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 25.06.15.
 * <p>
 *     BTC context
 * </p>
 */
public interface Context {

    /**
     * Getting global executor service
     * @return ExecutorService
     */
    ExecutorService getGlobalExecutor();
}
