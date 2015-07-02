package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.integration.models.factories.IntegrationMessageFactory;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestObject;
import pl.btcgrouppl.btc.backend.commons.test.util.integration.AbstractSampleSubscriber;
import rx.observables.BlockingObservable;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
@TestPropertySource("classpath:/test-application-jms.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationEventPublisherIntegrationTest {

    private static final int TIMEOUT_SEC = 30;

    @Autowired
    @Qualifier("integrationEventPublisher")
    private EventPublisher integrationEventPublisher;

    @Autowired
    private AbstractSampleSubscriber sampleSubscriber1;

    @Test
    public void testPublish() throws Exception {
        Given("Initialized integration event publisher, and rx blocking observables waiting on integration messages");
        TestObject testEvent = TestObject.builder().x(20).y("Test").build();
        IntegrationMessage expectedIntegrationMessage = IntegrationMessageFactory.create(testEvent);
        BlockingObservable<IntegrationMessage> integrationMessageBlockingObservable = sampleSubscriber1.asObservable().timeout(TIMEOUT_SEC, TimeUnit.SECONDS).take(1).toBlocking();

        When("Sending event");
        integrationEventPublisher.publish(testEvent);

        Then("All general channel subscribers should be notified about message");
        IntegrationMessage actualIntegrationMessage = integrationMessageBlockingObservable.first();
        assertEquals(expectedIntegrationMessage.getBody(), actualIntegrationMessage.getBody());
    }
}