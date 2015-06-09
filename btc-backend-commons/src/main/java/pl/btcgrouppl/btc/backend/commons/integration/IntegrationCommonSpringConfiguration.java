package pl.btcgrouppl.btc.backend.commons.integration;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;

import java.util.concurrent.Executors;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 05.06.15.
 * <p>
 *     Common integration spring configuration. It imports system-specific configurations by spring profiles configuration
 * </p>
 */
@Configuration
@EnableAutoConfiguration
//@EnableIntegration
@ComponentScan
//@IntegrationComponentScan
@Import(value = {IntegrationJmsSpringConfiguration.class})
public class IntegrationCommonSpringConfiguration {


    public static final String GENERAL_PUB_SUB_CHANNEL = Constants.INTEGRATION.GENERAL_CHANNEL;
    public static final String GENERAL_DESTINATION = Constants.INTEGRATION.GENERAL_DESTINATION;
    public static final String GENERAL_DITECT_CHANNEL = "generalDirectChannel";
    public static final String GENERAL_FLOW_IN = "generalFlowIn";
    public static final String GENERAL_FLOW_OUT = "generalFlowOut";


    /**
     * General messaging gateway. Use it in your business logic in order to send sth via spring integration
     */
    @MessagingGateway
    public interface GeneralMessageGateway {

        @Gateway(requestChannel = GENERAL_DITECT_CHANNEL)
        void sendMessage(IntegrationMessage message);
    }


    @Bean(name = GENERAL_DITECT_CHANNEL)
    @Qualifier(GENERAL_DITECT_CHANNEL)
    public DirectChannel flowOutDirectChannel() {
        return MessageChannels.direct(GENERAL_DITECT_CHANNEL).get();
    }

    @Bean(name = GENERAL_PUB_SUB_CHANNEL)
    @Qualifier(GENERAL_PUB_SUB_CHANNEL)
    public PublishSubscribeChannel generalPubSubMessageChannel() {
        return MessageChannels.publishSubscribe(GENERAL_PUB_SUB_CHANNEL, Executors.newCachedThreadPool()).get();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = Constants.JSON.SERIALIZERS_SET)
    @Qualifier(Constants.JSON.SERIALIZERS_SET)
    public ObjectMapper customizedObjectMapper(@Qualifier(Constants.JSON.INTEGRATION_DESERIALIZER) JsonDeserializer<IntegrationMessage> integrationMessageJsonDeserializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(IntegrationMessage.class, integrationMessageJsonDeserializer);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Spring integration Jackson object mapper
     */
    @Bean
    public Jackson2JsonObjectMapper intergationJackson2JsonObjectMapper(@Qualifier(Constants.JSON.SERIALIZERS_SET) ObjectMapper customizedObjectMapper) {
        return new Jackson2JsonObjectMapper(customizedObjectMapper);
    }

}
