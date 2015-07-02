package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.SimpleEventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.models.exceptions.EventExecutionException;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.DddUtil;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumer;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumerConditional;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestObject;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class SimpleEventHandlerTest {

    private static final Logger LOG = LogManager.getLogger(SimpleEventHandlerTest.class);

    @Autowired
    @Qualifier(BtcBackendCommonsTestSpringConfiguration.MOCK_INSTANCE)
    private SpElParserUtil mockSpElParserUtil;


    @Before
    public void setUp() {
    }

    @Test(expected = EventExecutionException.class)
    public void testHandleEventExecutionException() throws Exception {
        Given("Created instance of EventHandler. Handler has passed wrong instance of object");
        EventHandler testEventHandler = buildFakeEventHandler();

        When("Passing event to testEventHandler");
        testEventHandler.handle(TestObject.builder().x(20).y("Test").build());

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
        assertTrue(testEventConsumer.isConsumed());
        assertTrue(testEventConsumerConditional.isConsumed());
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
        assertFalse(testEventConsumerConditional.isConsumed());
    }


    /**
     * Creating simple event handler from consumer
     * @param testEventConsumer
     * @return EventHandler
     */
    protected EventHandler buildEventHandler(Object testEventConsumer) {
        Method consumerMethod = DddUtil.getConsumerMethod(testEventConsumer);
        if(consumerMethod==null) {
            throw new RuntimeException("Consumer method is null!");
        }
        EventHandler simpleEventHandler = SimpleEventHandler.builder()
                .instance(consumerMethod)
                .wrappedMethod(consumerMethod)
                .spElParserUtil(mockSpElParserUtil).build();
        return simpleEventHandler;
    }

    /**
     * Building fake event handler (for exceptions)
     * @return
     */
    protected EventHandler buildFakeEventHandler() {
        TestEventConsumer testEventConsumer = new TestEventConsumer();
        Method consumerMethod = DddUtil.getConsumerMethod(testEventConsumer);
        return SimpleEventHandler.builder()
                .instance(new Object())
                .spElParserUtil(mockSpElParserUtil)
                .wrappedMethod(consumerMethod)
                .build();
    }

}