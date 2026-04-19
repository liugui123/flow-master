/**
 * 工具类
 *
 * liugui
 * @date 2021/05/07 21:19
 * @Email liug@shinemo.com
 **/
package org.lg.engine.core.client.utils;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.lg.engine.core.client.exception.WfException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Utils {

//    private static final SpelParserConfiguration parserConfiguration = new SpelParserConfiguration(
//            SpelCompilerMode.IMMEDIATE, null);
//    private static final SpelExpressionParser expressionParser = new SpelExpressionParser(parserConfiguration);
//    private static final ParserContext parserContext = new ParserContext() {
//        @Override
//        public boolean isTemplate() {
//            return true;
//        }
//
//        @Override
//        public String getExpressionPrefix() {
//            return "${";
//        }
//
//        @Override
//        public String getExpressionSuffix() {
//            return "}";
//        }
//    };
//
//    /**
//     * 六位随机流水号
//     */
//    public static String getRandomNum(int length) {
//        Random random = new Random();
//        StringBuilder sNum = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            sNum.append(random.nextInt(10));
//        }
//        return sNum.toString();
//    }

//    /**
//     * 解析表达式
//     *
//     * @param tClass        返回类型
//     * @param <T>           返回类型
//     * @param mc            预算符号
//     * @param value         比较值
//     * @param comparisonVal 被比较 值
//     * liugui
//     * @date `2021/06/16` 15:21
//     */
//    public static <T> T parseExpression(Class<T> tClass, String mc, String value, String comparisonVal) {
//        //条件表达式
//        Object oValue = value, oComparisonVal = comparisonVal;
//        if (NumberUtils.isCreatable(value)) {
//            oValue = NumberUtils.createNumber(value);
//        }
//        if (NumberUtils.isCreatable(comparisonVal)) {
//            oComparisonVal = NumberUtils.createNumber(comparisonVal);
//        }
//        EvaluationContext evaluationContext = new StandardEvaluationContext();
//        if (ConditionEnum.ARRAY_CONTAINS.getCode().equals(mc) || ConditionEnum.BELONGTO_RANK.getCode().equals(mc)) {
//            String[] array = value.split(",");
//            List<String> datas = Arrays.asList(array);
//            evaluationContext.setVariable("_datas", datas);
//        }
//        String expressionString = getExpressionString(mc, oValue, oComparisonVal);
//        Expression parseExpression = expressionParser.parseExpression(expressionString, parserContext);
//        log.info(WfConstant.WF_LOG_PREFIX + "表达式:{}", parseExpression);
//        return parseExpression.getValue(evaluationContext, tClass);
//    }

//    /**
//     * 获取表达式
//     *
//     * @param mc            运算符
//     * @param value         值
//     * @param comparisonVal 比较值
//     * @return 表达式
//     */
//    private static String getExpressionString(String mc, Object value, Object comparisonVal) {
//        /**
//         * 相等( equal ) ：eq 不相等( not equal )： ne / neq 大于( greater than )： gt 小于( less than )： lt
//         * 大于等于( great than or equal ) ： ge/ gte 小于等于(  less than or equal )： le/lte
//         * 判断包含关系（字符串是否包含子串&&数组或集合是否包含元素）：”父串“.contains("子串")
//         * 满足任一 or 条件数组中任意一个满足时，返回true
//         */
//        String expression;
//        if (ConditionEnum.EQ_NULL.getCode().equals(mc) || ConditionEnum.NOT_NULL.getCode().equals(mc)) {
//            //字符串null特殊处理 null eq_null
//            if (value == null) {
//                expression = "${" + value + " " + mc + "}";
//            } else {
//                expression = "${'" + value + "' " + mc + "}";
//            }
//        } else if (ConditionEnum.CONTAINS.getCode().equals(mc)) {
//            //字符串contains特殊处理
//            expression = "${'" + value + "'." + mc + " ('" + comparisonVal + "')}";
//        } else if (ConditionEnum.ARRAY_CONTAINS.getCode().equals(mc) || ConditionEnum.BELONGTO_RANK.getCode().equals(mc)) {
//            //数组contains特殊处理
//            expression = "${#_datas." + "contains" + " ('" + comparisonVal + "')}";
//        } else if (ConditionEnum.NO_CONTAINS.getCode().equals(mc)) {
//            //字符串ncontains特殊处理
//            expression = "${!'" + value + "'." + "contains" + " ('" + comparisonVal + "')}";
//        } else if (value instanceof String && comparisonVal instanceof String) {
//            expression = "${'" + value + "' " + mc + " '" + comparisonVal + "'}";
//        } else if (value instanceof String) {
//            expression = "${'" + value + "' " + mc + " " + comparisonVal + "}";
//        } else if (comparisonVal instanceof String) {
//            expression = "${" + value + " " + mc + " '" + comparisonVal + "'}";
//        } else {
//            expression = "${" + value + " " + mc + " " + comparisonVal + "}";
//        }
//        return expression;
//    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isNotEmpty(Map m) {
        return m != null && m.size() > 0;
    }

    public static boolean isNotEmpty(Collection c) {
        return c != null && c.size() > 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }


    /**
     * hibernate-validator校验工具类
     * <p>
     * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
     *
     * admin
     */
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws WfException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws WfException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new WfException(msg.toString());
        }
    }


    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;

    }

    public static boolean isNotEmpty(Object[] o) {
        return o != null && o.length >0;

    }

//    public static JsonNode getValue(String json, String key) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = null;
//        try {
//            jsonNode = objectMapper.readTree(json);
//        } catch (JsonProcessingException e) {
////            Logs.error("parse json error", e);
//            throw new WfException("parse json error", e);
//        }
//        return jsonNode.get(key);
//    }

    public static boolean isNumeric(String s) {
        return StringUtils.isNumeric(s);
    }

    public static String getRandomNum(int length) {
        Random random = new Random();
        StringBuilder sNum = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sNum.append(random.nextInt(10));
        }
        return sNum.toString();
    }
}
