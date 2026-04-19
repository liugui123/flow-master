
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接口条件组
 */
@Getter
@Setter
public class ChildShapeApiConditionGroup extends ChildShapeConditionGroup{

    /**
     * 条件对象 存在具体条件
     */
    private List<ChildShapeApiCon> conditions;


}
