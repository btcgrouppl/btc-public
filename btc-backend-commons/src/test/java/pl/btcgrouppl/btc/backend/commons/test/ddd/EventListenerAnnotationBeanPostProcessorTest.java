package pl.btcgrouppl.btc.backend.commons.test.ddd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.ddd.events.EventPublisher;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.ddd.TestEventConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {BtcBackendCommonsTestSpringConfiguration.class})
public class EventListenerAnnotationBeanPostProcessorTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("defaultEventPublisher")
    private EventPublisher defaultEventPublisher;


    @Test
    public void testBeanPostProcessor() throws Exception {
        Given("Initialized application context");
        When("Creating new bean from prototype scope");
        TestEventConsumer testEventConsumer = applicationContext.getBean(TestEventConsumer.class);

        Then("It should have been properly created, and also it should be added to event dispatcher");
        assertNotNull(testEventConsumer);
        assertEquals(1, defaultEventPublisher.getHandlers().size());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}