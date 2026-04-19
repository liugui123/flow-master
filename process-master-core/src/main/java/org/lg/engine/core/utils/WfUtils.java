package org.lg.engine.core.utils;

import com.alibaba.fastjson2.JSON;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.utils.Utils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public class WfUtils {

    private static final SpelParserConfiguration parserConfiguration = new SpelParserConfiguration(
            SpelCompilerMode.IMMEDIATE, null);
    private static final SpelExpressionParser expressionParser = new SpelExpressionParser(parserConfiguration);
    private static final ParserContext parserContext = new ParserContext() {
        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionPrefix() {
            return "${";
        }

        @Override
        public String getExpressionSuffix() {
            return "}";
        }
    };

    public static String getJsonVal(String varJson, String expressionString) {
        try {
            if (Utils.isEmpty(expressionString)) {
                throw new WfException("标题表达式为空");
            }
            if (Utils.isEmpty(varJson)) {
                throw new WfException("流程变量为空");
            }
            Map<String, Object> map = JSON.parseObject(varJson, Map.class);
            if (Utils.isEmpty(map)) {
                throw new WfException("流程变量为空");
            }
            EvaluationContext evaluationContext = new StandardEvaluationContext();
            for (String key : map.keySet()) {
                evaluationContext.setVariable(key, map.get(key));
            }
            Expression parseExpression = expressionParser.parseExpression(expressionString, parserContext);
            Object value = parseExpression.getValue(evaluationContext);
            if (value == null) {
                return "";
            }
            return value.toString();
        } catch (Exception e) {
            Logs.error("表达式获取值错误：{},变量：{}，表达式:{}", e.getMessage(), varJson, expressionString);
            return "无标题";
        }
    }
}
