package pl.btcgrouppl.btc.backend.commons.integration.impl;

import lombok.NonNull;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationMessageAware;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message factory
 * </p>
 */
public class IntegrationMessageFactory {

    /**
     * IntegrationMessage creator. It checks, if object implements IntegrationMessageAware first. If so, it takes object
     * from {@link pl.btcgrouppl.btc.backend.commons.integration.IntegrationMessageAware#toIntegrationMessage()} method.
     * Otherwise it will transform it manually.
     * @param message
     * @return IntegrationMessage
     */
    public static IntegrationMessage create(@NonNull Object message) {
        if(message instanceof IntegrationMessageAware) {
            return ((IntegrationMessageAware)message).toIntegrationMessage();
        }
        return new IntegrationMessage(message.getClass().getName(), message);
    }
}
