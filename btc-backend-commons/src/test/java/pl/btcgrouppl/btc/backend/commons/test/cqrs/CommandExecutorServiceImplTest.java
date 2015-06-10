package pl.btcgrouppl.btc.backend.commons.test.cqrs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.models.exceptions.CqrsException;
import pl.btcgrouppl.btc.backend.commons.test.BtcBackendCommonsTestSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.*;
import rx.observables.BlockingObservable;

import static org.junit.Assert.assertEquals;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsTestSpringConfiguration.class)
public class CommandExecutorServiceImplTest {

    @Autowired
    private CommandExecutorService commandExecutorService;

    @Autowired
    @Qualifier("testCommand1Handler")
    private CommandHandler<TestCommand1, String> expectedCommandHandler;

    @Autowired
    @Qualifier("testAsyncCommand1Handler")
    private CommandHandler<TestAsyncCommand, String> expectedAsyncCommandHandler;

    @Test
    public void testExecuteSync() throws Exception {
        Given("Initialized instance of CommandExecutionService, and sample TestCommand1Handler");

        When("Executing execution handler for sample command");
        BlockingObservable<Object> actualAsObservable = commandExecutorService.execute(new TestCommand1(TestCommand1Handler.MAGIC_TXT)).toBlocking();

        Then("Proper result should be returned");
        assertEquals(TestCommand1Handler.ANSWEAR_OK, actualAsObservable.first());
    }

    @Test
    public void testExecuteAsync() throws Exception {
        Given("Initialized instance of CommandExecutionService, and sample TestAsyncCommandHandler");

        When("Executing execution handler for sample command (async one)");
        BlockingObservable<Object> actualAsObservable = commandExecutorService.execute(new TestAsyncCommand()).toBlocking();

        Then("Proper result should be returned");
        assertEquals(TestAsyncCommandHandler.TXT, actualAsObservable.first());
    }

    @Test(expected = CqrsException.class)
    public void testExecuteNoCommandHandlerError() throws Exception {
        Given("Initialized instance of CommandExecutionService");

        When("Executing execution handler for wrong command");
        commandExecutorService.execute(new TestCommand2());

        Then("CqrsException should be thrown");
    }
}