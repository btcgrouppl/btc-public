package pl.btcgrouppl.btc.backend.commons.integration.subscribers;

import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 */
public interface IntegrationSubscriber {

    void onIntegrationMessage(IntegrationMessageEvent integrationMessageEvent);
}
