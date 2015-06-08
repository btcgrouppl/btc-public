package pl.btcgrouppl.btc.backend.commons.integration.models;

import lombok.Data;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message. Wrapper for all sort of objects (commands, events), that can be transported
 *     via spring integration
 * </p>
 */
@Data
public class IntegrationMessage {

    private String messageClass;
    private Object body;

}
