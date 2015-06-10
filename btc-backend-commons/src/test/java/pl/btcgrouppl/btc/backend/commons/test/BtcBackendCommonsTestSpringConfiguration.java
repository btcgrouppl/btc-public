package pl.btcgrouppl.btc.backend.commons.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;

import java.util.UUID;

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

    @Bean
    @Qualifier(TEST_INSTANCE)
    public IntegrationMessage testIntegrationMessage() {
        TestCommand1 testCommand1 = new TestCommand1(TEST_INSTANCE);
        return new IntegrationMessage(UUID.randomUUID(), testCommand1.getClass().getName(), testCommand1);
    }
}
