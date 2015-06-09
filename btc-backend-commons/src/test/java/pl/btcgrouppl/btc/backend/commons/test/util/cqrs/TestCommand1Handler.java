package pl.btcgrouppl.btc.backend.commons.test.util.cqrs;

import org.springframework.beans.factory.annotation.Qualifier;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.models.annotations.CommandHandlerAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 */
@CommandHandlerAnnotation
@Qualifier("testCommand1Handler")
public class TestCommand1Handler implements CommandHandler<TestCommand1, String> {

    public static final String MAGIC_TXT = "showMeMagic";
    public static final String ANSWEAR_OK = "Here you go!";
    public static final String ANSWEAR_FAIL = "Nope!";

    @Override
    public String handleCommand(TestCommand1 command) {
        if(command.getSampleField().equals(MAGIC_TXT)) {
            return ANSWEAR_OK;
        }
        return ANSWEAR_FAIL;
    }
}