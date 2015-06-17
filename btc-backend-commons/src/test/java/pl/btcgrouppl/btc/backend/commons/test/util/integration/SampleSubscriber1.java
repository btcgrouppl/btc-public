package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Sample subscriber 1
 * </p>
 */
@IntegrationSubscriberAnnotation
public class SampleSubscriber1 extends AbstractSampleSubscriber {

    public static final String TAG = "Subscriber1";

    @Override
    public String getSubscriberId() {
        return TAG;
    }
}