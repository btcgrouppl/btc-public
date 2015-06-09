package pl.btcgrouppl.btc.backend.commons.integration;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
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
@EnableIntegration
@ComponentScan
@IntegrationComponentScan
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
    public DirectChannel provideFlowOutDirectChannel() {
        return MessageChannels.direct(GENERAL_DITECT_CHANNEL).get();
    }

    @Bean(name = GENERAL_PUB_SUB_CHANNEL)
    @Qualifier(GENERAL_PUB_SUB_CHANNEL)
    public PublishSubscribeChannel provideGeneralPubSubMessageChannel() {
        return MessageChannels.publishSubscribe(GENERAL_PUB_SUB_CHANNEL, Executors.newCachedThreadPool()).get();
    }


    /**
     * Jackson object mappers
     */
    @Bean
    @Qualifier(Constants.JSON.SERIALIZER_CLEAR)
    public ObjectMapper provideClearObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Qualifier(Constants.JSON.SERIALIZERS_SET)
    public ObjectMapper provideCustomizedObjectMapper() {
        return new ObjectMapper();
    }

}
