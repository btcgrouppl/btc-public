package pl.btcgrouppl.btc.backend.commons.test.util;

import lombok.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.btcgrouppl.btc.backend.commons.utils.SpElParserUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {SpElParserUtilImplTest.class})
public class SpElParserUtilImplTest {

    @Autowired
    private SpElParserUtil spElParserUtilImpl;

    @Test
    public void testParseExpressionWithRootObject() throws Exception {
        Given("Initialized instance of SpElParserUtilImpl, string expression and instances of TestObject");
        final TestObject trueResultObject = TestObject.builder().x(20).y("Test").build();
        final TestObject falseResultObject = TestObject.builder().x(25).y("Test").build();
        final String expression = "#{x==20 and y=='Test'}";

        When("Trying parse on with passed root object");
        Boolean actualTrueResult = spElParserUtilImpl.parseExpression(expression, trueResultObject, Boolean.class);
        Boolean actualFalseResult = spElParserUtilImpl.parseExpression(expression, falseResultObject, Boolean.class);

        Then("Expression should be properly parsed");
        assertTrue(actualTrueResult);
        assertFalse(actualFalseResult);
    }

    @Test
    public void testParseExpressionWithPropertyObjects() throws Exception {
        Given("Initialized instance of SpElParserUtilImpl, string expression, instances of TestObject and ");
        final TestObject trueResultObject = TestObject.builder().x(20).y("Test").build();
        final TestObject falseResultObject = TestObject.builder().x(25).y("Test").build();
        final String expression = "#{object.x==20 and object.y=='Test'}";

        Map<String, Object> trueParseContext = new HashMap<>();
        trueParseContext.put("object", trueResultObject);

        Map<String, Object> falseParseContext = new HashMap<>();
        falseParseContext.put("object", trueResultObject);


        When("Trying parse on with passed root object");
        Boolean actualTrueResult = spElParserUtilImpl.parseExpression(expression, trueParseContext, Boolean.class);
        Boolean actualFalseResult = spElParserUtilImpl.parseExpression(expression, falseParseContext, Boolean.class);

        Then("Expression should be properly parsed");
        assertTrue(actualTrueResult);
        assertFalse(actualFalseResult);
    }


    /**
     * Test class
     */
    @Builder
    private class TestObject {
        public int x;
        public String y;
    }
}