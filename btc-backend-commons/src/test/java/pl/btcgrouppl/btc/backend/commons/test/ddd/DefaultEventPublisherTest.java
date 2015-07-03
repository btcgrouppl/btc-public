package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.DddUtil;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.RxAsyncEventHandlerWrapper;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumer;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import rx.observables.BlockingObservable;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {BtcBackendCommonsTestSpringConfiguration.class})
public class DefaultEventPublisherTest {

    private static final int TIMEOUT_SEC = 30;

    @Autowired
    @Qualifier(BtcBackendCommonsTestSpringConfiguration.MOCK_INSTANCE)
    private SpElParserUtil mockSpElParserUtil;

    @Autowired
    @Qualifier("defaultEventPublisher")
    private EventPublisher defaultEventPublisher;

    /**
     * Currently used event handler wrapper
     */
    private RxAsyncEventHandlerWrapper currentEventHandler;


    @Before
    public void setUp() {
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        currentEventHandler = new RxAsyncEventHandlerWrapper(new SimpleEventHandler(
                DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, mockSpElParserUtil
        ));
    }

    @After
    public void tearDown() {
        defaultEventPublisher.removeHandler(currentEventHandler);
    }

    @Test
    public void testAddHandler() throws Exception {
        Given("Initialized instance, created TestEventConsumer, and created RxAsyncEventHandlerWrapper");

        When("Adding new event handler");
        defaultEventPublisher.addHandler(currentEventHandler);

        Then("No of handlers should have been increased");
        assertEquals(1, defaultEventPublisher.getHandlers().size());
    }

    @Test
    public void testPublish() throws Exception {
        Given("Initialized instance, created TestEventConsumer, and created RxAsyncEventHandlerWrapper");
        defaultEventPublisher.addHandler(currentEventHandler);
        BlockingObservable<Boolean> booleanBlockingObservable = currentEventHandler.asObservable().timeout(TIMEOUT_SEC, TimeUnit.SECONDS).toBlocking();

        When("Dispatching event");
        defaultEventPublisher.publish(new Object());
        Boolean actualListenerAnswear = booleanBlockingObservable.last();

        Then("Our async handler wrapper should be invoked");
        assertTrue(actualListenerAnswear);
    }
}