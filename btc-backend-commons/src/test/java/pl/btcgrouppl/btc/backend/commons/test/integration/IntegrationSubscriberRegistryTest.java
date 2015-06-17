package pl.btcgrouppl.btc.backend.commons.test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.impl.IntegrationSubscriberRegistry;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tumbler.Tumbler.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     IntegrationSubscriberRegistry test
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class IntegrationSubscriberRegistryTest {

    public static final String SAMPLE_SUB_1_BEAN = "sampleSubscriber1";
    public static final String SAMPLE_SUB_2_BEAN = "sampleSubscriber2";

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private IntegrationSubscriberRegistry integrationSubscriberRegistry;

    @Test
    public void testGetSubscribersToHandlers() throws Exception {
        Given("Instance of registry, and counting no. of subscribers objects");
        Map<String, IntegrationSubscriber> expectedIntegrationSubscribers = getBeansOfType(IntegrationSubscriber.class);

        When("Getting this information from registry");
        Map<String, ?> actualSubscribersToHandlers = integrationSubscriberRegistry.getSubscribersToHandlers();

        Then("Correct data should be returned (and correct channel subscriptions)");
        assertEquals(expectedIntegrationSubscribers.size(), actualSubscribersToHandlers.size());

        Set<String> expectedSubscribers = expectedIntegrationSubscribers.keySet();
        Set<String> actualSubscribers = actualSubscribersToHandlers.keySet();
        assertTrue(expectedSubscribers.containsAll(actualSubscribers));
        assertTrue(actualSubscribers.contains(SAMPLE_SUB_1_BEAN));
        assertTrue(actualSubscribers.contains(SAMPLE_SUB_2_BEAN));
    }

    @Test
    public void testGetChannelsToBeanNames() throws Exception {
        Given("Instance of registry, and counting no. of PublishSubscribeChannels in system");
        Map<String, PublishSubscribeChannel> expectedPubSubChannels = getBeansOfType(PublishSubscribeChannel.class);

        When("Getting this information from registry");
        Map<String, String> actualChannelsToBeanNames = integrationSubscriberRegistry.getChannelsToBeanNames();

        Then("Correct data should be returned");
        assertEquals(expectedPubSubChannels.size(), actualChannelsToBeanNames.size());

        for(String beanName: expectedPubSubChannels.keySet()) {
            assertTrue(actualChannelsToBeanNames.containsValue(beanName));
        }
    }

    /**
     * Getting beans for type
     * @param type
     * @param <T>
     * @return Map<String, T>
     */
    protected <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> beansOfType = beanFactory.getBeansOfType(type);
        return beansOfType;
    }
}