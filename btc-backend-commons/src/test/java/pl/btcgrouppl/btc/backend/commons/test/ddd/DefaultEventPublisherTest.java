package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.DddUtil;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.RxAsyncEventHandlerWrapper;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumer;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import rx.observables.BlockingObservable;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DefaultEventPublisherTest.class})
public class DefaultEventPublisherTest {

    @Autowired
    private SpElParserUtil spElParserUtilImpl;

    @Autowired
    @Qualifier("defaultEventPublisher")
    private EventPublisher defaultEventPublisher;

    @Test
    public void testAddHandler() throws Exception {
        Given("Initialized instance, created TestEventConsumer, and created RxAsyncEventHandlerWrapper");
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        RxAsyncEventHandlerWrapper rxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(new SimpleEventHandler(
                DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, spElParserUtilImpl
        ));
        BlockingObservable<Boolean> booleanBlockingObservable = rxAsyncEventHandlerWrapper.asObservable().toBlocking();

        When("Adding new event handler");
        defaultEventPublisher.addHandler(rxAsyncEventHandlerWrapper);

        Then("No of handlers should have been increased");
        assertEquals(1, defaultEventPublisher.getHandlers().size());
    }

    @Test
    public void testPublish() throws Exception {
        Given("Initialized instance, created TestEventConsumer, and created RxAsyncEventHandlerWrapper");
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        RxAsyncEventHandlerWrapper rxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(new SimpleEventHandler(
                DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, spElParserUtilImpl
        ));
        defaultEventPublisher.addHandler(rxAsyncEventHandlerWrapper);
        BlockingObservable<Boolean> booleanBlockingObservable = rxAsyncEventHandlerWrapper.asObservable().toBlocking();

        When("Dispatching event");
        defaultEventPublisher.publish(new Object());
        Boolean actualListenerAnswear = booleanBlockingObservable.last();

        Then("Our async handler wrapper should be invoked");
        assertTrue(actualListenerAnswear);
    }
}