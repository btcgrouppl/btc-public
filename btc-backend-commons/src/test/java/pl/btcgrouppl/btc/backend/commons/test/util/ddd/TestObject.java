package pl.btcgrouppl.btc.backend.commons.test.util.ddd;


import com.google.common.base.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventAnnotation;


/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 * <p>
 *     Test class used for DDD tests
 * </p>
 */
@Component
@Scope("prototype")
@EventAnnotation(isExternal = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {

    public static final Predicate<TestObject> SP_EL_RESULT_TRUE_PREDICATE = new Predicate<TestObject>() {

        @Override
        public boolean apply(TestObject input) {
            return input.getX()>20;
        }
    };

    public static final Predicate<TestObject> SP_EL_RESULT_FALSE_PREDICATE = new Predicate<TestObject>() {

        @Override
        public boolean apply(TestObject input) {
            return input.getX()<=20;
        }
    };

    private int x;
    private String y;

}