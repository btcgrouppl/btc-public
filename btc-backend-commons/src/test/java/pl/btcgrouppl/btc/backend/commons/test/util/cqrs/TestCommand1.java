package pl.btcgrouppl.btc.backend.commons.test.util.cqrs;

import lombok.*;
import pl.btcgrouppl.btc.backend.commons.cqrs.models.annotations.CommandAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 * <p>
 *     Sample command used in order to test.<br />
 *     Command is not async for the test purpose.
 * </p>
 */
@CommandAnnotation(isAsync = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCommand1 {

    private String sampleField;
}
