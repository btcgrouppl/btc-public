package pl.btcgrouppl.btc.backend.commons.test.util.cqrs;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.btcgrouppl.btc.backend.commons.cqrs.annotations.CommandAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 * <p>
 *     Sample command used in order to test.<br />
 *     Command is not async for the test purpose.
 * </p>
 */
@CommandAnnotation(isAsync = false)
@Data
@AllArgsConstructor
public class TestCommand1 {

    private String sampleField;
}
