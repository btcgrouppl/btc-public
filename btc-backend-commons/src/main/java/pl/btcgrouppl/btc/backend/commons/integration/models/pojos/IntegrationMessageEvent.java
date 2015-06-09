package pl.btcgrouppl.btc.backend.commons.integration.models.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Integration message event. Passed to every IntegrationSubscriber. payload contains converted object (from JSON or another meta-data
 *     format)
 * </p>
 */
@Data
@AllArgsConstructor
public class IntegrationMessageEvent {

    @NonNull
    private Map<String, Object> headers;
    @NonNull
    private Object payload;

    /**
     * Creating from spring integration Message
     * @param message
     * @return IntegrationMessageEvent
     */
    public static IntegrationMessageEvent fromMessage(@NonNull Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        return new IntegrationMessageEvent(headers, message.getPayload());
    }
}