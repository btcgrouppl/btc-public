package pl.btcgrouppl.btc.backend.commons;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.cqrs.impl.CommandExecutorServiceFactoryBean;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationCommonSpringConfiguration;

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