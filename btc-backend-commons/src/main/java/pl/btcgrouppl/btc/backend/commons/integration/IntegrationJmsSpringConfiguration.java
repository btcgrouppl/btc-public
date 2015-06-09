package pl.btcgrouppl.btc.backend.commons.integration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.jms.core.JmsTemplate;
import pl.btcgrouppl.btc.backend.commons.Constants;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 05.06.15.
 * <p>
 *      JMS configuration for spring integration. Enable proper profile (integration-jms) to use this context
 * </p>
 */
@Configuration
@Profile(Constants.PROFILES.PROFILE_INTEGRATION_JMS)
@EnableAutoConfiguration
@EnableIntegration
@ComponentScan
@IntegrationComponentScan
public class IntegrationJmsSpringConfiguration {

    @Bean(name = IntegrationCommonSpringConfiguration.GENERAL_FLOW_IN)
    @Qualifier(IntegrationCommonSpringConfiguration.GENERAL_FLOW_IN)
    public IntegrationFlow generalChannelIn(JmsTemplate jmsTemplate, @Qualifier(IntegrationCommonSpringConfiguration.GENERAL_PUB_SUB_CHANNEL) PublishSubscribeChannel generalPubSubChannel) {
        return IntegrationFlows.from(Jms.inboundAdapter(jmsTemplate).destination(IntegrationCommonSpringConfiguration.GENERAL_DESTINATION))
                .transform(Transformers.fromJson()) //TODO custom object mapper for IntegrationMessage
                .channel(generalPubSubChannel)
                .get();
    }

    @Bean(name = IntegrationCommonSpringConfiguration.GENERAL_FLOW_OUT)
    @Qualifier(IntegrationCommonSpringConfiguration.GENERAL_FLOW_OUT)
    public IntegrationFlow generalChannelOut(JmsTemplate jmsTemplate, @Qualifier(IntegrationCommonSpringConfiguration.GENERAL_DITECT_CHANNEL) DirectChannel directOutChannel) {
        return IntegrationFlows.from(directOutChannel)
                .transform(Transformers.toJson())   //TODO custom object mapper for IntegrationMessage
                .handle(Jms.outboundAdapter(jmsTemplate).destination(IntegrationCommonSpringConfiguration.GENERAL_DESTINATION))
                .get();
    }

}