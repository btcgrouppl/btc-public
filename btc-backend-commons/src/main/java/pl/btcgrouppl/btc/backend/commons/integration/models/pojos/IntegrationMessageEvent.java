package pl.btcgrouppl.btc.backend.commons.integration.models.pojos;

import lombok.Data;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Integration message event. Passed to every IntegrationSubscriber. payload contains converted object (from JSON or another meta-data
 *     format)
 * </p>
 */
@Data
public class IntegrationMessageEvent {

    private Map<String, Object> headers;
    private Object payload;
}