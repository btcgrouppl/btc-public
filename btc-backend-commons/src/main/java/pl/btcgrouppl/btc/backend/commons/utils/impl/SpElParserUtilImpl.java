package pl.btcgrouppl.btc.backend.commons.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;
import pl.btcgrouppl.btc.backend.commons.utils.models.exceptions.SpElParseException;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 26.06.15.
 * <p>
 * Standard implementation of SpEL expression util.
 * </p>
 */
@Component
@Qualifier("spElParserUtil")
public class SpElParserUtilImpl implements SpElParserUtil {

    private ExpressionParser spelExpressionParser;

    @Autowired
    public SpElParserUtilImpl(ExpressionParser spelExpressionParser) {
        this.spelExpressionParser = spelExpressionParser;
    }

    @Override
    public <T> T parseExpression(String expression, Object root, Class<T> returnClass) {
        try {
            Expression spElExpression = spelExpressionParser.parseExpression(expression);
            StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(root);
            return spElExpression.getValue(standardEvaluationContext, returnClass);
        } catch (ParseException | EvaluationException e) {
            throw new SpElParseException(e);
        }
    }

    @Override
    public <T> T parseExpression(String expression, Map<String, Object> variables, Class<T> returnClass) {
        try {
            Expression spElExpression = spelExpressionParser.parseExpression(expression);
            StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(variables);
            standardEvaluationContext.setPropertyAccessors(Arrays.asList(new MapAccessor(), new ReflectivePropertyAccessor()));
            return spElExpression.getValue(standardEvaluationContext, returnClass);
        } catch (ParseException | EvaluationException e) {
            throw new SpElParseException(e);
        }
    }
}
