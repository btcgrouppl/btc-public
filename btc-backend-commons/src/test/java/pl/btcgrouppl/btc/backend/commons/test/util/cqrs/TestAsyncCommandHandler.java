package pl.btcgrouppl.btc.backend.commons.test.util.cqrs;

import org.springframework.beans.factory.annotation.Qualifier;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.annotations.CommandHandlerAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.05.15.
 */
@CommandHandlerAnnotation
@Qualifier("testAsyncCommand1Handler")
public class TestAsyncCommandHandler implements CommandHandler<TestAsyncCommand, String> {

    public static final String TXT = "test";

    @Override
    public String handleCommand(TestAsyncCommand command) {
        return TXT;
    }
}
