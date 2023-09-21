package com.github.shadow.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Jexl3UtilsTest {

    @Test
    void buildContextName() {
        String contextName = "test";
        System.out.println("Jex3Utils.buildContextName = " + Jexl3Utils.buildContextName(contextName));
    }

    @Test
    void convertJexlExpression() {
        List<String> selects = Arrays.asList("a", "b", "c");
        String expression = "( a + b ) * ( a - b ) / ( b + c )";
        System.out.println("Jex3Utils.convertJexlExpression = " + Jexl3Utils.convertJexlExpression(selects, expression));
    }

    @Test
    void checkExpression() {
        List<String> selects = Arrays.asList("a", "b", "c");
        String expression = "( a + b ) * ( a - b ) / ( b + c )";
        Jexl3Utils.checkExpression(selects, expression);
    }

    @Test
    void mathEvaluate() {
        Map<String, Object> source = new HashMap<>();
        source.put("a", 1.23);
        source.put("b", 2.45);
        source.put("c", 3.75);
        String expression = "( a + b ) * ( a - b ) / ( b + c )";
        Object evaluate = Jexl3Utils.mathEvaluate(source, expression);
        System.out.println("evaluate = " + evaluate);
    }



    @Test
    void convertBigDecimalValue() {
        BigDecimal n1 = Jexl3Utils.convertBigDecimalValue(123);
        BigDecimal n2 = Jexl3Utils.convertBigDecimalValue(1.23f);
        BigDecimal n3 = Jexl3Utils.convertBigDecimalValue(1.23e-3);
        BigDecimal n4 = Jexl3Utils.convertBigDecimalValue(123456L);
        BigDecimal n5 = Jexl3Utils.convertBigDecimalValue(2);
        BigDecimal n6 = Jexl3Utils.convertBigDecimalValue(new BigInteger("987654321"));
        String format = String.format("n1={%s}; n2={%s}; n3={%s}; n4={%s}; n5={%s}; n6={%s};", n1.toPlainString(),
            n2.toPlainString(), n3.toPlainString(), n4.toPlainString(), n5.toPlainString(), n6.toPlainString());
        System.out.println("format = " + format);
    }

}