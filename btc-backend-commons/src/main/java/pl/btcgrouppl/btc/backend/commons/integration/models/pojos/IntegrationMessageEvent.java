package pl.btcgrouppl.btc.backend.commons.integration.models.pojos;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.messaging.Message;

import java.util.Collections;
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
    private IntegrationMessage integrationMessage;
    private Optional<Object> integrationMessageBody;




    /**
     * Creating from spring integration Message. Getting headers and IntegrationMessage's body
     * @param message
     * @return IntegrationMessageEvent
     */
    public static IntegrationMessageEvent fromMessage(@NonNull Message<?> message) {
        Map<String, Object> headers = (message.getHeaders()==null) ? Collections.<String, Object>emptyMap() : message.getHeaders();
        IntegrationMessage integrationMessageBody = (IntegrationMessage)Preconditions.checkNotNull(message.getPayload());
        return new IntegrationMessageEvent(headers, integrationMessageBody, Optional.fromNullable(integrationMessageBody.getBody()));
    }
}