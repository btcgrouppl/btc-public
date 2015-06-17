package pl.btcgrouppl.btc.backend.commons.integration.models.factories;

import lombok.NonNull;
import pl.btcgrouppl.btc.backend.commons.integration.models.IntegrationMessageAware;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

import java.util.UUID;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message factory
 * </p>
 */
public class IntegrationMessageFactory {

    /**
     * IntegrationMessage creator. It checks, if object implements IntegrationMessageAware first. If so, it takes object
     * from {@link pl.btcgrouppl.btc.backend.commons.integration.models.IntegrationMessageAware#toIntegrationMessage()} method.
     * Otherwise it will transform it manually.
     * @param message
     * @return IntegrationMessage
     */
    public static IntegrationMessage create(@NonNull Object message) {
        UUID messageUUID = UUID.randomUUID();
        return create(messageUUID, message);
    }

    /**
     * IntegrationMessage creator. It checks, if object implements IntegrationMessageAware first. If so, it takes object
     * from {@link pl.btcgrouppl.btc.backend.commons.integration.models.IntegrationMessageAware#toIntegrationMessage()} method.
     * Otherwise it will transform it manually.
     * @param message
     * @param uuid message identifier
     * @return IntegrationMessage
     */
    public static IntegrationMessage create(@NonNull UUID uuid, @NonNull Object message) {
        if(message instanceof IntegrationMessageAware) {
            return ((IntegrationMessageAware) message).toIntegrationMessage();
        }
        return new IntegrationMessage(uuid, message.getClass().getName(), message);
    }
}
