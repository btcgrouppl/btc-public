package pl.btcgrouppl.btc.backend.commons.integration.converters;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

import java.io.IOException;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Custom JSON deserializer
 * </p>
 */
@Component
@Qualifier(Constants.JSON.INTEGRATION_DESERIALIZER)
public class IntegrationMessageDeserializer extends JsonDeserializer<IntegrationMessage> {

    @Override
    public IntegrationMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        //jp.ne
        return null;
    }
}
