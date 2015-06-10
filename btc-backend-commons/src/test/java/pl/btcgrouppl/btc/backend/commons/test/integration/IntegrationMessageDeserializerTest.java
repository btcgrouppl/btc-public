package pl.btcgrouppl.btc.backend.commons.test.integration;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;

import java.util.UUID;

import static org.junit.Assert.*;
import static tumbler.Tumbler.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     Integration message deserializer test
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class IntegrationMessageDeserializerTest {

    @Autowired
    @Qualifier(Constants.JSON.INTEGRATION_DESERIALIZER)
    private JsonDeserializer<IntegrationMessage> integrationMessageJsonDeserializer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier(BtcBackendCommonsTestSpringConfiguration.TEST_INSTANCE)
    private IntegrationMessage expectedIntegrationMessage;


    @Before
    public void setUp() throws Exception {
        //Setting custom module with our deserializer
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(IntegrationMessage.class, integrationMessageJsonDeserializer);
        objectMapper.registerModule(simpleModule);
    }

    @Test
    public void testDeserialize() throws Exception {
        Given("Instance of deserializer, and serialized integration message instance");
        String json = objectMapper.writeValueAsString(expectedIntegrationMessage);

        When("Trying to deserialized JSON");
        IntegrationMessage actualIntegrationMessage = objectMapper.readValue(json, IntegrationMessage.class);

        Then("Properly deserialized instance returned");
        assertNotNull(actualIntegrationMessage);
        assertEquals(expectedIntegrationMessage, actualIntegrationMessage);
    }


}