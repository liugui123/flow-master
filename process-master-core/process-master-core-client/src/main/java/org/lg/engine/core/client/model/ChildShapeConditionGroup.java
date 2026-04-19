
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点上配置的条件
 * 理想情况下需要支持多个条件组，条件组内可以设置关系，比如& ｜
 * 多个条件组返回的结果，再通过设置的条件关系返回最终结果
 */
@Getter
@Setter
public class ChildShapeConditionGroup {

    private String name;

    /**
     * rel_条件关系 and 条件都必须满足 or 任意条件满足
     */
    private String rel;

}
