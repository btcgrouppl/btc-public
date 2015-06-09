package pl.btcgrouppl.btc.backend.commons.integration.models.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message. Wrapper for all sort of objects (commands, events), that can be transported
 *     via spring integration
 * </p>
 */
@Data
@AllArgsConstructor
public class IntegrationMessage {

    @NonNull
    UUID uuid;

    @NonNull
    private String messageClass;

    @NonNull
    private Object body;

}