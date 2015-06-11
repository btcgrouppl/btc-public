package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Sample subscriber 1
 * </p>
 */
@IntegrationSubscriberAnnotation
public class SampleSubscriber1 implements IntegrationSubscriber {

    private static final Logger LOG = LogManager.getLogger(SampleSubscriber1.class);

    @Override
    public void onIntegrationMessage(IntegrationMessageEvent integrationMessageEvent) {
        LOG.debug("Sample subscriber1: " + integrationMessageEvent);
    }

}
