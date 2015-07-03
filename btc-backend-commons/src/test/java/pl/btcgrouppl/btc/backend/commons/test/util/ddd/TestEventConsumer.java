package pl.btcgrouppl.btc.backend.commons.test.util.ddd;


import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventListenerAnnotation;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 * <p>
 *     Test, non-conditional event consumer
 * </p>
 */
@Component
@Scope("prototype")
public class TestEventConsumer {

    @Getter
    private boolean consumed = false;

    @EventListenerAnnotation
    public void consume(Object event) {
        consumed = true;
    }

    public void clear() {
        consumed = false;
    }
}
