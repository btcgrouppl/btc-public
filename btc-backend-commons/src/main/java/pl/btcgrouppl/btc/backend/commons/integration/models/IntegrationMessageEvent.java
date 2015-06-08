package pl.btcgrouppl.btc.backend.commons.integration.models;

import lombok.Data;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Integration message event. Passed to every IntegrationSubscriber
 * </p>
 */
@Data
public class IntegrationMessageEvent<T> {

    private Map<String, Object> headers;
    private T payload;
}