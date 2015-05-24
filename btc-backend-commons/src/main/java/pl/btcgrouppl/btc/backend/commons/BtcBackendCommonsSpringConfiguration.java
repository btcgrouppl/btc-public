package pl.btcgrouppl.btc.backend.commons;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.cqrs.impl.CommandExecutorServiceFactoryBean;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BtcBackendCommonsSpringConfiguration {

    @Bean
    FactoryBean<CommandExecutorService> provideCommandExecutorService(CommandHandlerRegistry commandHandlerRegistry) {
        return new CommandExecutorServiceFactoryBean(commandHandlerRegistry);
    }
}