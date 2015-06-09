package pl.btcgrouppl.btc.backend.commons.integration.converters;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.factories.IntegrationMessageFactory;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Custom JSON deserializer designed for integration messages
 * </p>
 */
@Component
@Qualifier(Constants.JSON.INTEGRATION_DESERIALIZER)
public class IntegrationMessageDeserializer extends JsonDeserializer<IntegrationMessage> {

    private static final Logger LOG = LogManager.getLogger(IntegrationMessageDeserializer.class);
    public static final String UUID = "uuid";
    public static final String MESSAGE_CLASS = "messageClass";
    public static final String BODY = "body";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public IntegrationMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        String uuid = jsonNode.get(UUID).asText();
        String bodyClass = jsonNode.get(MESSAGE_CLASS).asText();
        try {
            String body = jsonNode.get(BODY).asText();
            Class<?> bodyClazz = Class.forName(bodyClass);
            Object bodyAsObject = objectMapper.readValue(body, bodyClazz);
            return IntegrationMessageFactory.create(bodyAsObject);
        } catch (ClassNotFoundException | NullPointerException e) {
            LOG.error("Error while processing message with ID: " + uuid + ".", e);
            throw new JsonParseException(e.getMessage(), JsonLocation.NA, e);
        }
    }
}
