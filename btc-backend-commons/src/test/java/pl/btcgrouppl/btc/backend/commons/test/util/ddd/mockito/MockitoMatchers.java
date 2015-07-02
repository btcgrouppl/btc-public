package pl.btcgrouppl.btc.backend.commons.test.util.ddd.mockito;

import com.google.common.base.Predicate;
import org.mockito.ArgumentMatcher;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 03.07.15.
 * <p>
 *     Mockito default matchers set
 * </p>
 */
public class MockitoMatchers {

    /**
     * Matching whole map. Using guava's {@link com.google.common.base.Predicate}
     * @param predicate
     * @return ArgumentMatcher<Map<String, Object>>
     */
    public static ArgumentMatcher<Map<String, Object>> mapArgumentMatcher(final Predicate<Map<String, Object>> predicate) {
        return argumentMatcher(predicate);
    }

    /**
     * Generic argument matcher
     * @param predicate
     * @param <T>
     * @return ArgumentMatcher<T>
     */
    public static <T> ArgumentMatcher<T> argumentMatcher(final Predicate<T> predicate) {
        return new ArgumentMatcher<T>() {
            @Override
            public boolean matches(Object argument) {
                return predicate.apply((T)argument);
            }
        };
    }

}
