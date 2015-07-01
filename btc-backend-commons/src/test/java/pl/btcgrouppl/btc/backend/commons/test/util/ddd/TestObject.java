package pl.btcgrouppl.btc.backend.commons.test.util.ddd;


import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 * <p>
 *     Test class used for DDD tests
 * </p>
 */
@Data
@Builder
@Component
@Scope("prototype")
public class TestObject {

    private int x;
    private String y;
}