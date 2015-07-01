package pl.btcgrouppl.btc.backend.commons.test.ddd;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.AsyncEventHandlerWrapper;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventConditionAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.DddUtil;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.RxAsyncEventHandlerWrapper;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumer;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import rx.Observable;
import rx.observables.BlockingObservable;
import rx.subjects.PublishSubject;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AsyncEventHandlerWrapperTest.class})
public class AsyncEventHandlerWrapperTest {

    @Autowired
    private SpElParserUtil spElParserUtilImpl;


    @Test
    public void testHandle() throws Exception {
        Given("Initialized async handler wrapper, and wrapped SimpleEventHandler");
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, spElParserUtilImpl);
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
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, spElParserUtilImpl);
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
        SimpleEventHandler testEventHandler = new SimpleEventHandler(DddUtil.getConsumerMethod(testEventConsumer), testEventConsumer, spElParserUtilImpl);
        RxAsyncEventHandlerWrapper testRxAsyncEventHandlerWrapper = new RxAsyncEventHandlerWrapper(testEventHandler);
        TestObject testObject = TestObject.builder().x(22).build();

        When("Checking, if simple event handler is applicable for consumer");
        boolean actual = testRxAsyncEventHandlerWrapper.isEventApplicable(testObject);

        Then("Proper result should be returned");
        assertTrue(actual);
    }

    /**
     * Event consumer throwing {@link pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException}
     */
    private class ExceptionEventConsumer {

        public boolean consumed = false;

        @EventListenerAnnotation
        public void consume(Object event) {
            throw EventExecutionException.create("Is is expected!", EventExecutionException.TYPE.EVENT_DISPATCH_FAIL);
        }
    }

    /**
     * Conditional event consumer with conditional expression
     */
    private class TestEventConsumerConditional {

        public boolean consumed = false;

        @EventListenerAnnotation(isAsync = false)   //Checking just sync version
        @EventConditionAnnotation(expression = "#{x>20}")
        public void consume(Object event) {
            consumed = true;
        }
    }

    /**
     * Test class
     */
    @Builder
    private class TestObject {
        public int x;
        public String y;
    }
}