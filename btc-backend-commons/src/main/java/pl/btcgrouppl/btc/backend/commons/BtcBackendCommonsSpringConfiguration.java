package pl.btcgrouppl.btc.backend.commons;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.jms.JmsInboundChannelAdapterSpec;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.cqrs.impl.CommandExecutorServiceFactoryBean;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationCommonSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationJmsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.impl.IntegrationMessage;

import java.util.concurrent.Executors;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 */
@Configuration
@ComponentScan
@IntegrationComponentScan
@EnableAutoConfiguration
@EnableIntegration
@Import(value = {IntegrationCommonSpringConfiguration.class})
public class BtcBackendCommonsSpringConfiguration {

    @Bean
    public FactoryBean<CommandExecutorService> provideCommandExecutorService(CommandHandlerRegistry commandHandlerRegistry) {
        return new CommandExecutorServiceFactoryBean(commandHandlerRegistry);
    }
}