
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 节点上配置的条件
 * 理想情况下需要支持多个条件组，条件组内可以设置关系，比如& ｜
 * 多个条件组返回的结果，再通过设置的条件关系返回最终结果
 */
@Getter
@Setter
public class ChildShapeCondition {

    /**
     * conditionType_条件类型 自定义 1 默认 0
     * 数字
     */
    private Integer conditionType = 0;

    /**
     * 条件组的 rel_条件关系 and 都必须满足 or 任意满足
     */
    private String rel;

    /**
     * 表单条件组集合，多个条件组返回条件结果
     */
    private List<ChildShapeFormConditionGroup> formConditionGroups;

    /**
     * API条件组集合，多个条件组返回条件结果
     *
     * Service
     */
    private List<ChildShapeApiConditionGroup> apiConditionGroups;

}
