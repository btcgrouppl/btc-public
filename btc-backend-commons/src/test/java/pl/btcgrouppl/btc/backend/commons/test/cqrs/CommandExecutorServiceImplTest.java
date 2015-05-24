package pl.btcgrouppl.btc.backend.commons.test.cqrs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.BtcBackendCommonsSpringConfiguration;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.exceptions.CqrsException;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand1Handler;
import pl.btcgrouppl.btc.backend.commons.test.util.cqrs.TestCommand2;
import rx.Observable;
import rx.observables.BlockingObservable;

import static org.junit.Assert.*;
import static tumbler.Tumbler.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = BtcBackendCommonsSpringConfiguration.class)
public class CommandExecutorServiceImplTest {

    @Autowired
    private CommandExecutorService commandExecutorService;

    @Autowired
    @Qualifier("testCommand1Handler")
    private CommandHandler<TestCommand1, String> expectedCommandHandler;

    @Test
    public void testExecute() throws Exception {
        Given("Initialized instance of CommandExecutionService, and sample TestCommand1Handler");

        When("Executing execution handler for sample command");
        BlockingObservable<Object> actualAsObservable = commandExecutorService.execute(new TestCommand1(TestCommand1Handler.MAGIC_TXT)).toBlocking();

        Then("Proper result should be returned");
        assertEquals(TestCommand1Handler.ANSWEAR_OK, actualAsObservable.first());
    }

    @Test(expected = CqrsException.class)
    public void testExecuteNoCommandHandlerError() throws Exception {
        Given("Initialized instance of CommandExecutionService");

        When("Executing execution handler for wrong command");
        commandExecutorService.execute(new TestCommand2());

        Then("CqrsException should be thrown");
    }
}