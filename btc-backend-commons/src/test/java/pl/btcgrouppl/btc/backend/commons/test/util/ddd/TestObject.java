package pl.btcgrouppl.btc.backend.commons.test.util.ddd;


import lombok.Builder;
import lombok.Data;
import org.mockito.ArgumentMatcher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.ddd.models.annotations.EventAnnotation;


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
@EventAnnotation(isExternal = true)
public class TestObject {

    private int x;
    private String y;


    /**
     * True result argument matcher on TestObject. Used for mock ver of SpElParserUtil
     */
    public static class SpElResultTrueArgumentMatcher implements ArgumentMatcher<TestObject> {

        @Override
        public boolean matches(Object argument) {
            TestObject castedArgument = (TestObject)argument;
            return castedArgument.getX()>20;
        }
    }

    /**
     * False result matcher on TestObject. Used for mock ver of SpElParserUtil
     */
    public static class SpElResultFalseArgumentMatcher implements ArgumentMatcher<TestObject> {

        @Override
        public boolean matches(Object argument) {
            TestObject castedArgument = (TestObject)argument;
            return castedArgument.getX()<=20;
        }
    }
}