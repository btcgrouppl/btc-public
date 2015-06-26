package pl.btcgrouppl.btc.backend.commons.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 *     Standard implementation of SpEL expression util.
 * </p>
 */
@Component
public class SpElParserUtilImpl implements SpElParserUtil {

    private ExpressionParser spelExpressionParser;

    @Autowired
    public SpElParserUtilImpl(ExpressionParser spelExpressionParser) {
        this.spelExpressionParser = spelExpressionParser;
    }

    @Override
    public <T> T parseExpression(String expression, Object root, Class<T> returnClass) {
        Expression spElExpression = spelExpressionParser.parseExpression(expression);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(root);
        return spElExpression.getValue(standardEvaluationContext, returnClass);
    }

    @Override
    public <T> T parseExpression(String expression, Map<String, Object> variables, Class<T> returnClass) {
        Expression spElExpression = spelExpressionParser.parseExpression(expression);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariables(variables);
        return spElExpression.getValue(standardEvaluationContext, returnClass);
    }
}
