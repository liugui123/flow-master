package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class NextActResponse extends BaseWfResponse {

    private Long id;


    private String actKey;


    private String actName;


    private String actType;

    private Boolean filterSelf;

//    private ChildShapeAssigneeInfo assigneeInfo;

    private String nodeMode;

    /**
     * 用户模式
     */
    private String viewConf;

    /**
     * 是否多部门
     */
    private Boolean isAllDept;

    /**
     * 当前节点是否必选节点
     */
    private Boolean required;

    /**
     * 处理人信息
     */
    private TaskUserTreeResponse taskUserTree;

}
