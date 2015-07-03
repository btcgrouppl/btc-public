package pl.btcgrouppl.btc.backend.commons.test.integration;

import com.google.common.collect.Iterables;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.integration.IntegrationCommonSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.TestConstants;
import pl.btcgrouppl.btc.backend.commons.test.util.integration.SampleSubscriber1;
import pl.btcgrouppl.btc.backend.commons.test.util.integration.SampleSubscriber2;
import rx.observables.BlockingObservable;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static tumbler.Tumbler.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     IntegrationSubscriberRegistry INTEGRATION test. Testing with spring integration infrastructure.
 *     It will fail when integration provider (mongo, activeMx) will be not available.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
@TestPropertySource(TestConstants.OTHERS.TEST_PROP_SOURCE_JMS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationSubscriberRegistryIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationSubscriberRegistryIntegrationTest.class);

    public static final int MESSAGES_TO_SEND = 1;
    public static final int TIMEOUT = 30;

    @Autowired
    private SampleSubscriber1 sampleSubscriber1;

    @Autowired
    private SampleSubscriber2 sampleSubscriber2;

    @Autowired
    private IntegrationCommonSpringConfiguration.GeneralMessageGateway generalMessageGateway;

    @Autowired
    private BtcBackendCommonsTestSpringConfiguration.TestChannelMessageGateway testChannelMessageGateway;

    @Autowired
    @Qualifier(BtcBackendCommonsTestSpringConfiguration.TEST_INSTANCE)
    private IntegrationMessage integrationMessage;

    @After
    public void tearDown() {
    }


    @Test
    public void testSendIntegrationMessages() {
        Given("Initialized messaging server and spring integration");
        BlockingObservable<IntegrationMessage> generalChannelMessageObservable = sampleSubscriber1.asObservable()
                .take(MESSAGES_TO_SEND).timeout(TIMEOUT, TimeUnit.SECONDS).toBlocking();
        BlockingObservable<IntegrationMessage> generalAndTestChannelMessageObservable = sampleSubscriber2.asObservable()
                .take(MESSAGES_TO_SEND * 2).timeout(TIMEOUT, TimeUnit.SECONDS).toBlocking();      //two channels to subscribe

        When("Sending message to publish/subscribe channels");
        for(int i=0; i<MESSAGES_TO_SEND; i++) {
            generalMessageGateway.sendMessage(integrationMessage);
            testChannelMessageGateway.sendTestMessage(integrationMessage);
        }

        Then("Messages should be handled by IntegrationSubscribers");
        Iterable<IntegrationMessage> integrationMessagesGeneralChannel = generalChannelMessageObservable.toIterable();
        Iterable<IntegrationMessage> integrationMessagesGeneralAndTestChannel = generalAndTestChannelMessageObservable.toIterable();

        assertEquals(MESSAGES_TO_SEND, Iterables.size(integrationMessagesGeneralChannel));
        assertEquals(MESSAGES_TO_SEND * 2, Iterables.size(integrationMessagesGeneralAndTestChannel));     //two channels to subscribe
    }

}