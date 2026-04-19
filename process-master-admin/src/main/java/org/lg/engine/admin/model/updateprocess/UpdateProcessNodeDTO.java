package org.lg.engine.admin.model.updateprocess;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.ChildProcessBtnAuth;

import java.util.List;

@Getter
@Setter
public class UpdateProcessNodeDTO {

    private String resourceId;
    private org.lg.engine.core.client.model.ChildShapeProperties properties;
    private String actType;
    private List<org.lg.engine.core.client.model.ChildShapeOutgoing> outgoing;
    private org.lg.engine.core.client.model.ChildSubProcess subProcess;
    private UpdateProcessNodeShapeUserDTO assigneeInfo;
    private UpdateProcessNodeShapeUserDTO noneAssignee;
    private UpdateProcessNodeShapeUserDTO informerInfo;
    private UpdateProcessNodeShapeUserDTO addSignAssigneeInfo;
    private UpdateProcessNodeShapeUserDTO forwardAssigneeInfo;
    private UpdateProcessNodeShapeUserDTO addAssigneeInfo;
    private String viewConf;
    private Integer multipleAssignee;
    private org.lg.engine.core.client.model.ChildShapeCondition condition;
    private Integer passMode;
    private org.lg.engine.core.client.model.ActMqConfig mqConfig;
    private org.lg.engine.core.client.model.CommentPermission commentPermission;
    private List<String> backAbleActKeys;
    private Boolean backOut;
    private Boolean filterSelf;
    private org.lg.engine.core.client.model.ActInstUrgeConfig urgeConfig;
    private String nodeMode;
    private org.lg.engine.core.client.model.TaskLabel taskLabel;
    private org.lg.engine.core.client.model.SignConfig signConfig;
    private Boolean isAllDept;
    private Boolean required;
    private List<org.lg.engine.core.client.model.Service> services;
    private List<org.lg.engine.core.client.model.ChildFormFieldsAuth> formFieldsAuth;
    private List<ChildProcessBtnAuth> processBtnAuth;

}
