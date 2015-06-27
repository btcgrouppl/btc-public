package pl.btcgrouppl.btc.backend.commons.test.ddd;

import lombok.Builder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.ConditionalEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventConditionAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SimpleEventHandlerTest.class)
public class SimpleEventHandlerTest {

    private static final Logger LOG = LogManager.getLogger(SimpleEventHandlerTest.class);

    @Autowired
    private SpElParserUtil spElParserUtilImpl;


    @Before
    public void setUp() {
    }

    @Test(expected = EventExecutionException.class)
    public void testHandleEventExecutionException() throws Exception {
        Given("Created instance of EventHandler. Handler has passed wrong instance of object");
        EventHandler testEventHandler = buildFakeEventHandler();

        When("Passing event to testEventHandler");
        testEventHandler.handle(new TestObject(20, "Test"));

        Then("EventExecutionException should be thrown");
    }


    @Test
    public void testIsEventApplicableTrue() throws Exception {
        Given("Created instance of TestObject, instance of TestEventConsumer and TestEventConsumerConditional");
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        TestEventConsumerConditional testEventConsumerConditional = new TestEventConsumerConditional();

        TestObject trueConditionalEvent = TestObject.builder().x(22).build();

        EventHandler nonConditionalEventHandler = buildEventHandler(testEventConsumer);
        EventHandler conditionalEventHandler = buildEventHandler(testEventConsumerConditional);

        When("Checking, whether passed TestObject instances are applicable to consumers");
        conditionalEventHandler.handle(trueConditionalEvent);
        nonConditionalEventHandler.handle(trueConditionalEvent);

        Then("TRUE boolean value should be returned");
        assertTrue(testEventConsumer.consumed);
        assertTrue(testEventConsumerConditional.consumed);
    }

    @Test
    public void testIsEventApplicableFalse() throws Exception {
        Given("Created instance of TestObject, instance of TestEventConsumer and TestEventConsumerConditional");
        TestEventConsumerConditional testEventConsumerConditional = new TestEventConsumerConditional();
        TestObject falseConditionalEvent = TestObject.builder().x(20).build();
        EventHandler conditionalEventHandler = buildEventHandler(testEventConsumerConditional);

        When("Checking, whether passed TestObject instances are applicable to consumers");
        conditionalEventHandler.handle(falseConditionalEvent);

        Then("FALSE boolean value should be returned on conditional consumer");
        assertFalse(testEventConsumerConditional.consumed);
    }


    /**
     * Creating simple event handler from consumer
     * @param testEventConsumer
     * @return EventHandler
     */
    protected EventHandler buildEventHandler(Object testEventConsumer) {
        Method consumerMethod = getConsumerMethod(testEventConsumer);
        if(consumerMethod==null) {
            throw new RuntimeException("Consumer method is null!");
        }
        EventHandler simpleEventHandler = SimpleEventHandler.builder()
                .instance(consumerMethod)
                .wrappedMethod(consumerMethod)
                .spElParserUtil(spElParserUtilImpl).build();
        return simpleEventHandler;
    }

    protected EventHandler buildFakeEventHandler() {
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        Method consumerMethod = getConsumerMethod(testEventConsumer);
        return SimpleEventHandler.builder()
                .instance(new Object())
                .spElParserUtil(spElParserUtilImpl)
                .wrappedMethod(consumerMethod)
                .build();
    }

    /**
     * Getting consumer method
     * @return Method
     */
    protected Method getConsumerMethod(Object source) {
        Class<?> aClass = source.getClass();
        Method[] methods = aClass.getMethods();
        for(Method item: methods) {
            if(item.isAnnotationPresent(EventListenerAnnotation.class)) {
                return item;
            }
        }
        return null;
    }


    /**
     * Event consumer without any conditions
     */
    private class TestEventConsumer {

        public boolean consumed = false;

        @EventListenerAnnotation
        public void consume(Object event) {
            consumed = true;
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