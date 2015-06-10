package pl.btcgrouppl.btc.backend.commons.test.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.impl.IntegrationSubscriberRegistry;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     IntegrationSubscriberRegistry test
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class IntegrationSubscriberRegistryTest {

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

        Then("Correct data should be returned");
        assertEquals(expectedIntegrationSubscribers.size(), actualSubscribersToHandlers.size());

        Set<String> expectedSubscribers = expectedIntegrationSubscribers.keySet();
        Set<String> actualSubscribers = actualSubscribersToHandlers.keySet();
        assertTrue(expectedSubscribers.contains(actualSubscribers));
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