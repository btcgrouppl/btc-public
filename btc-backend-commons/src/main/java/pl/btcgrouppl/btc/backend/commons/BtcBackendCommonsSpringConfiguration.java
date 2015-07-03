package pl.btcgrouppl.btc.backend.commons;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.cqrs.impl.CommandExecutorServiceFactoryBean;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.DelegatingEventPublisher;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationCommonSpringConfiguration;

import java.util.LinkedList;
import java.util.List;

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
    public FactoryBean<CommandExecutorService> commandExecutorService(CommandHandlerRegistry commandHandlerRegistry) {
        return new CommandExecutorServiceFactoryBean(commandHandlerRegistry);
    }

    @Bean
    @Qualifier(Constants.QUALIFIERS.DELEGATING_PUBLISHER)
    public EventPublisher delegatingEventPubisher(@Qualifier(Constants.QUALIFIERS.DEFAULT_PUBLISHER) EventPublisher defaultEventPublisher,
                                                  @Qualifier(Constants.QUALIFIERS.INTEGRATION_PUBLISHER) EventPublisher integrationEventPublisher) {
        List<EventPublisher> publisherList = new LinkedList<>();
        publisherList.add(defaultEventPublisher);
        publisherList.add(integrationEventPublisher);
        return new DelegatingEventPublisher(publisherList);
    }

    @Bean
    public ExpressionParser spelExpressionParser() {
        return new SpelExpressionParser();
    }
}