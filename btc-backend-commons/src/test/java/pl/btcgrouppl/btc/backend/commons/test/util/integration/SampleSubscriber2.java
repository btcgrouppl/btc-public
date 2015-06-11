package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 */
@IntegrationSubscriberAnnotation(channels = {"errorChannel", "generalChannel"})
public class SampleSubscriber2 implements IntegrationSubscriber {

    private static final Logger LOG = LogManager.getLogger(SampleSubscriber2.class);

    @Override
    public void onIntegrationMessage(IntegrationMessageEvent integrationMessageEvent) {
        LOG.debug("Sample subscriber2: " + integrationMessageEvent);
    }
}
