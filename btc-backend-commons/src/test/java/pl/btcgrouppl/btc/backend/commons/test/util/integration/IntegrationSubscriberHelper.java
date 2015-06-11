package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import rx.Observable;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Wrapper for convinient usage for tests
 * </p>
 */
public interface IntegrationSubscriberHelper {

    /**
     * Counting number of sent messages to subscriber for given channel
     * @param channelName
     * @return Observable<Integer>
     */
    Observable<Integer> countForChannel(String channelName);

    /**
     * Refreshing all counters logic
     */
    void refresh() throws Exception;

    /**
     * Increasing for channel
     * @param channel
     */
    void increase(String channel);
}
