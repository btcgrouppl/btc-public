package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Wrapper for convinient usage for tests
 * </p>
 */
public interface IntegrationSubscriberHelper {

    /**
     * Counting number of sent messages to subscriber for given channel
     * @return Observable<IntegrationMessage>
     */
    Observable<IntegrationMessage> asObservable();

    /**
     * Notify about new integation message
     * @param integrationMessage
     */
    void notify(IntegrationMessage integrationMessage);

    /**
     * Adding observable to replay subject
     * @param subscriber
     * @return Subscription
     */
    Subscription subscribe(Action1<IntegrationMessage> subscriber);
}
