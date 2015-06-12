package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import pl.btcgrouppl.btc.backend.commons.test.TestConstants;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 */
@IntegrationSubscriberAnnotation(channels = {TestConstants.INTEGRATION.TEST_CHANNEL_PUBSUB, Constants.INTEGRATION.GENERAL_CHANNEL})
public class SampleSubscriber2 extends AbstractSampleSubscriber {
}
