package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点自动提醒类型配置
 * liugui
 * @Date 2022/2/15
 */
@Getter
@Setter
public class RemindAssigneeConfig {

    /**
     * 被提醒人
     */
    private ChildShapeAssigneeInfo assigneeInfo;

}
