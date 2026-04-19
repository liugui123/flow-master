
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单条件
 */
@Getter
@Setter
public class ChildShapeFormCon extends ChildShapeCon{

    /**
     * key
     * 字段的标识
     */
    private String subjectKey;

    /**
     * 字段的名字
     */
    private String subjectName;

    /**
     * mc_运算符
     * 比如等于
     * mc_运算符 等于 eq，不等于 ne,大于 gt,大于等于 ge，小于 lt，
     * 小于等于 le,为空使用 eq_null,不为空使用 ne_null,
     * 包含 contains，不包含 ncontains 满足任一 or
     * 数组包含 array_contains
     */
    private String mc;

    /**
     * 条件中的期望的值
     * 比如销售部 加入存入的是id 123
     */
    private String comparisonVal;

}
