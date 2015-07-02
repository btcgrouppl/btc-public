package pl.btcgrouppl.btc.backend.commons.test.util.ddd;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventConditionAnnotation;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;
import pl.btcgrouppl.btc.backend.commons.test.TestConstants;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 */
@Data
@NoArgsConstructor
@Component
@Scope("prototype")
public class TestEventConsumerConditional {

    private boolean consumed = false;

    @EventListenerAnnotation(isAsync = false)   //Checking just sync version
    @EventConditionAnnotation(expression = TestConstants.DDD.SPEL_OBJECT_CONDITIONAL_EXPRESSION)
    public void consume(Object event) {
        consumed = true;
    }

    public void clear() {
        consumed = false;
    }

}