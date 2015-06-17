package pl.btcgrouppl.btc.backend.commons.test.cqrs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static tumbler.Tumbler.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class CommandHandlerRegistryImplTest {

    @Autowired
    private CommandHandlerRegistry commandHandlerRegistry;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    @Qualifier("testCommand1Handler")
    private CommandHandler<TestCommand1, String> expectedCommandHandler;

    /**
     * Classical testing command handler
     * @throws Exception
     */
    @Test
    public void testGetCommandHandler() throws Exception {
        Given("Components scanned, context refreshed, our test command handler registered");

        When("Try to retrieve handler for TestCommand1 command");
        CommandHandler actualCommandHandler = commandHandlerRegistry.getCommandHandler(TestCommand1.class);

        Then("Getting proper instance");
        assertNotNull(actualCommandHandler);
        assertEquals(expectedCommandHandler, actualCommandHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCommandHandlerNonExistingCommand() throws Exception {
        Given("Components scanned, context refreshed");

        When("Getting wrong command");
        commandHandlerRegistry.getCommandHandler(TestCommand2.class);

        Then("IllegalArgumentException expeted");
    }
}