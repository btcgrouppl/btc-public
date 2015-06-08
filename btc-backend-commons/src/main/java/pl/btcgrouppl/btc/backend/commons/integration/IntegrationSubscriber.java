package pl.btcgrouppl.btc.backend.commons.integration;

import pl.btcgrouppl.btc.backend.commons.integration.models.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.IntegrationMessageEvent;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 */
public interface IntegrationSubscriber {

    void onIntegrationMessage(IntegrationMessageEvent integrationMessageEvent);
}
