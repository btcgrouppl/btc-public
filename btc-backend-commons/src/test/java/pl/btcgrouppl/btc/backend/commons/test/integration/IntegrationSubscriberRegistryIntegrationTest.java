package pl.btcgrouppl.btc.backend.commons.test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.integration.SampleSubscriber1;
import pl.btcgrouppl.btc.backend.commons.test.util.integration.SampleSubscriber2;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 10.06.15.
 * <p>
 *     IntegrationSubscriberRegistry INTEGRATION test. Testing with spring integration infrastructure.
 *     It will fail when integration provider (mongo, activeMx) will be not available.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class IntegrationSubscriberRegistryIntegrationTest {

    @Autowired
    private SampleSubscriber1 sampleSubscriber1;

    @Autowired
    private SampleSubscriber2 sampleSubscriber2;

    @Autowired
    @Qualifier(Constants.INTEGRATION.GENERAL_CHANNEL)
    private PublishSubscribeChannel generaprivatelPubSubChannel;

    @Test
    public void testSendIntegrationMessages() {
        //TODO prepare
    }


}