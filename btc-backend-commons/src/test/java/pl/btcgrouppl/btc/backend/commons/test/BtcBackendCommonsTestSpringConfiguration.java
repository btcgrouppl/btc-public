package pl.btcgrouppl.btc.backend.commons.test;

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
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestObject;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.util.UUID;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     BTC backend commons TEST spring configuration
 * </p>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@IntegrationComponentScan
@EnableIntegration
@Import({BtcBackendCommonsSpringConfiguration.class})
public class BtcBackendCommonsTestSpringConfiguration {

    public static final String TEST_INSTANCE = "TEST_INSTANCE";
    public static final String MOCK_INSTANCE = "MOCK_INSTANCE";


    @Bean
    @Qualifier(TEST_INSTANCE)
    public IntegrationMessage testIntegrationMessage() {
        TestCommand1 testCommand1 = new TestCommand1(TEST_INSTANCE);
        return new IntegrationMessage(UUID.randomUUID(), testCommand1.getClass().getName(), testCommand1);
    }

    /**
     * Test spring integration config
     */
    @MessagingGateway
    public interface TestChannelMessageGateway {

        @Gateway(requestChannel = TestConstants.INTEGRATION.TEST_CHANNEL_DIRECT)
        void sendTestMessage(IntegrationMessage integrationMessage);
    }

    @Bean(name = TestConstants.INTEGRATION.TEST_CHANNEL_DIRECT)
    @Qualifier(TestConstants.INTEGRATION.TEST_CHANNEL_DIRECT)
    public DirectChannel testDirectChannel() {
        return MessageChannels.direct(TestConstants.INTEGRATION.TEST_CHANNEL_DIRECT).get();
    }

    @Bean(name = TestConstants.INTEGRATION.TEST_CHANNEL_PUBSUB)
    @Qualifier(TestConstants.INTEGRATION.TEST_CHANNEL_PUBSUB)
    public PublishSubscribeChannel testPubSubChannel() {
        return MessageChannels.publishSubscribe(TestConstants.INTEGRATION.TEST_CHANNEL_PUBSUB, Executors.newCachedThreadPool()).get();
    }

    @Bean(name = TestConstants.INTEGRATION.TEST_FLOW_OUT)
    @Qualifier(TestConstants.INTEGRATION.TEST_FLOW_OUT)
    public IntegrationFlow testFlowOut(@Qualifier(TestConstants.INTEGRATION.TEST_CHANNEL_DIRECT) DirectChannel testDirectChannel, JmsTemplate jmsTemplate,
                                       Jackson2JsonObjectMapper jackson2JsonObjectMapper) {
        return IntegrationFlows.from(testDirectChannel)
                .transform(Transformers.toJson(jackson2JsonObjectMapper))
                .handle(Jms.outboundAdapter(jmsTemplate).destination(TestConstants.INTEGRATION.TEST_DESTINATION))
                .get();
    }

    @Bean(name = TestConstants.INTEGRATION.TEST_FLOW_IN)
    @Qualifier(TestConstants.INTEGRATION.TEST_FLOW_IN)
    public IntegrationFlow testFlowIn(@Qualifier(TestConstants.INTEGRATION.TEST_CHANNEL_PUBSUB) PublishSubscribeChannel testPubSubChannel, JmsTemplate jmsTemplate,
                                      JsonToObjectTransformer integrationMessageJsonToObjectTransformer) {
        return IntegrationFlows.from(Jms.inboundAdapter(jmsTemplate).destination(TestConstants.INTEGRATION.TEST_DESTINATION))
                .transform(integrationMessageJsonToObjectTransformer)
                .channel(testPubSubChannel)
                .get();
    }

    /**
     * Mocked version of SpElParserUtil class.
     * @return SpElParserUtil
     */
    @Bean
    @Qualifier(MOCK_INSTANCE)
    public SpElParserUtil mockParserUtil() {
        SpElParserUtil mockSpElParserUtil = mock(SpElParserUtil.class);
        when(mockSpElParserUtil.parseExpression(
                eq(TestConstants.DDD.SPEL_CONDITIONAL_EXPRESSION),
                argThat(new TestObject.SpElResultTrueArgumentMatcher()),
                eq(Boolean.class)
        )).thenReturn(Boolean.TRUE);
        when(mockSpElParserUtil.parseExpression(
                eq(TestConstants.DDD.SPEL_CONDITIONAL_EXPRESSION),
                argThat(new TestObject.SpElResultFalseArgumentMatcher()),
                eq(Boolean.class)
        )).thenReturn(Boolean.FALSE);
        return mockSpElParserUtil;
    }


}
