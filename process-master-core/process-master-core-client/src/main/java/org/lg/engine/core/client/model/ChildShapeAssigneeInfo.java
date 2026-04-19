
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@ToString
public class ChildShapeAssigneeInfo{

    /**
     * 任务处理人
     */
    private Collection<Assignee> assignees;

    /**
     * 是否来自发起人
     */
    private Boolean fromStarter;
    /**
     * 来自表单处理人
     * 格式  Set<Assignee>
     */
    private String formUserKey;

    /**
     * 来自节点??
     */
    private List<String> fromNodes;

    /**
     * 接口中的查询
     */
    private Service service;

}
