package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.*;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import rx.observables.BlockingObservable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AsyncEventHandlerWrapperTest.class})
public class AsyncEventHandlerWrapperTest {

    @Autowired
    @Qualifier(BtcBackendCommonsTestSpringConfiguration.MOCK_INSTANCE)
    private SpElParserUtil mockSpElParserUtil;


    @Test
    public void testHandle() throws Exception {
        Given("Initialized async handler wrapper, and wrapped SimpleEventHandler");
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, mockSpElParserUtil);
        RxAsyncEventHandlerWrapper testRxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(testEventHandler);

        When("Invoking async handler wrapper");
        BlockingObservable<Boolean> booleanBlockingObservable = testRxAsyncEventHandlerWrapper
                .asObservable()
                .toBlocking();
        testRxAsyncEventHandlerWrapper.handle(new Object());
        Boolean executionBoolean = booleanBlockingObservable.last();

        Then("It should be properly executed in separate thread");
        assertTrue(executionBoolean);
    }

    @Test
    public void testOnFailure() throws Exception {
        Given("Initialized async handler wrapper, and wrapped SimpleEventHandler");
        ExceptionEventConsumer testEventConsumer = new ExceptionEventConsumer();
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, mockSpElParserUtil);
        RxAsyncEventHandlerWrapper testRxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(testEventHandler);

        When("Invoking async handler wrapper, exception is expected during execution");
        BlockingObservable<Boolean> booleanBlockingObservable = testRxAsyncEventHandlerWrapper
                .asObservable()
                .toBlocking();
        testRxAsyncEventHandlerWrapper.handle(new Object());
        Boolean executionBoolean = booleanBlockingObservable.last();

        Then("Method onFailure should be executed on handler");
        assertFalse(executionBoolean);
    }

    @Test
    public void testIsEventApplicable() throws Exception {
        Given("Initialized async handler wrapper, and wrapped SimpleEventHandler");
        TestEventConsumerConditional testEventConsumer = new TestEventConsumerConditional();
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, mockSpElParserUtil);
        RxAsyncEventHandlerWrapper testRxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(testEventHandler);
        TestObject testObject = TestObject.builder().x(22).build();

        When("Checking, if simple event handler is applicable for consumer");
        boolean actual = testRxAsyncEventHandlerWrapper.isEventApplicable(testObject);

        Then("Proper result should be returned");
        assertTrue(actual);
    }



}