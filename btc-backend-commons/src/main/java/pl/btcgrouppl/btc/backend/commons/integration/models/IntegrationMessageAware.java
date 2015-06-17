package pl.btcgrouppl.btc.backend.commons.integration.models;

import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message aware interface. Transforming message on both sides before/after out system communication
 * </p>
 */
public interface IntegrationMessageAware {

    IntegrationMessage toIntegrationMessage();

    void fromIntegrationMessage(IntegrationMessage message);
}
