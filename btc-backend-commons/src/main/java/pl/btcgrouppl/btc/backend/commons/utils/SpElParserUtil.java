package pl.btcgrouppl.btc.backend.commons.utils;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 *     Spring expression parser util
 * </p>
 */
public interface SpElParserUtil {

    public static final String SPEL_EVENT_ARG_NAME = "event";

    /**
     * Parsing expression with root variable
     * @param expression
     * @param root
     * @param <T>
     * @return
     */
    <T> T parseExpression(String expression, Object root, Class<T> returnClass);

    /**
     * Parsing expression with map of variables
     * @param expression
     * @param variables
     * @param <T>
     * @return
     */
    <T> T parseExpression(String expression, Map<String, Object> variables, Class<T> returnClass);
}
