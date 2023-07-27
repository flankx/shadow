package com.github.shadow.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl3.*;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Jexl3Utils {

    private final static JexlEngine JEXL_ENGINE = new JexlBuilder().cache(512).create();
    private final static String EXPRESSION_APPEND = "A";

    public static String buildContextName(String name) {
        return EXPRESSION_APPEND + name + EXPRESSION_APPEND;
    }

    /**
     * 格式化数据库表字段的计算表达式 [字符串前面字符是数字时会导致 jexl parse error ]
     *
     * @param columnSelects the column selects
     * @param expression the expression
     * @return string string
     */
    public static String convertJexlExpression(Collection<String> columnSelects, String expression) {
        if (StringUtils.isBlank(expression)) {
            return expression;
        }
        log.debug("before expression convert : {}", expression);
        for (String columnName : columnSelects) {
            expression = expression.replaceAll(columnName, buildContextName(columnName));
        }
        log.debug("after expression convert : {}", expression);
        return expression;
    }

    /**
     * Check expression.
     *
     * @param selects the selects
     * @param expression the expression
     */
    public static void checkExpression(List<String> selects, String expression) {
        JexlContext context = new MapContext();
        for (String select : selects) {
            context.set(buildContextName(select), 1.0);
        }
        // 计算表达式
        String convertExpression = convertJexlExpression(selects, expression);
        JexlExpression jexlExpression = JEXL_ENGINE.createExpression(convertExpression);
        jexlExpression.evaluate(context);
    }

    /**
     * 执行计算表达式
     *
     * @param source the source
     * @param expression the expression
     * @return the object
     */
    public static Object mathEvaluate(Map<String, Object> source, String expression) {
        if (StringUtils.isBlank(expression)) {
            return null;
        }
        // 计算参数
        JexlContext jexlContext = new MapContext();
        for (String key : source.keySet()) {
            jexlContext.set(buildContextName(key), convertBigDecimalValue(source.get(key)));
        }
        // 计算表达式
        String convertExpression = convertJexlExpression(source.keySet(), expression);
        try {
            JexlExpression jexlExpression = JEXL_ENGINE.createExpression(convertExpression);
            return jexlExpression.evaluate(jexlContext);
        } catch (Exception e) {
            log.error("jexlExpression.evaluate(jexlContext) failed! error = ", e);
        }
        return null;
    }

    public static BigDecimal convertBigDecimalValue(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof Short || value instanceof Integer || value instanceof BigInteger) {
            return new BigDecimal(String.valueOf(value));
        }
        if (value instanceof Long) {
            return BigDecimal.valueOf((Long)value);
        }
        if (value instanceof Float) {
            return BigDecimal.valueOf((Float)value);
        }
        if (value instanceof Double) {
            return BigDecimal.valueOf((Double)value);
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        return BigDecimal.ZERO;
    }
}
