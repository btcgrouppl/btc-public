package pl.btcgrouppl.btc.backend.commons.test;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestObject;
import pl.btcgrouppl.btc.backend.commons.test.util.mockito.MockitoMatchers;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.util.Map;
import java.util.UUID;

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
@Import({BtcBackendCommonsSpringConfiguration.class, BtcBackendCommonsJmsTestSpringConfiguration.class})
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

    /**
     * Mocked version of SpElParserUtil class.
     * @return SpElParserUtil
     */
    @Bean
    @Qualifier(MOCK_INSTANCE)
    public SpElParserUtil mockParserUtil() {
        SpElParserUtil mockSpElParserUtil = mock(SpElParserUtil.class);
        when(mockSpElParserUtil.parseExpression(
                eq(TestConstants.DDD.SPEL_OBJECT_CONDITIONAL_EXPRESSION),
                argThat(MockitoMatchers.mapArgumentMatcher(new Predicate<Map<String, Object>>() {
                    @Override
                    public boolean apply(Map<String, Object> input) {
                        TestObject event = (TestObject)input.get("event");
                        return TestObject.SP_EL_RESULT_TRUE_PREDICATE.apply(event);
                    }
                })),
                eq(Boolean.class)
        )).thenReturn(Boolean.TRUE);
        when(mockSpElParserUtil.parseExpression(
                eq(TestConstants.DDD.SPEL_OBJECT_CONDITIONAL_EXPRESSION),
                argThat(MockitoMatchers.mapArgumentMatcher(new Predicate<Map<String, Object>>() {
                    @Override
                    public boolean apply(Map<String, Object> input) {
                        TestObject event = (TestObject)input.get("event");
                        return TestObject.SP_EL_RESULT_FALSE_PREDICATE.apply(event);
                    }
                })),
                eq(Boolean.class)
        )).thenReturn(Boolean.FALSE);
        return mockSpElParserUtil;
    }


}
