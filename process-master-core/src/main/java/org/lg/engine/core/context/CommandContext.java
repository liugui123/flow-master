package org.lg.engine.core.context;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.ChildShapeAssigneeInfo;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;
import org.lg.engine.core.db.model.*;

import java.util.List;

/**
 * 命令执行上下文
 * 每个线程执行单个命令时，该对象附加到线程上,线程执行结束后会被移除
 */
@Getter
@Setter
public class CommandContext extends BaseWfRequest {


    private ReProcess reProcess;
    /**
     * 流程实例
     */
    private RuProcinst ruProcinst;

    /**
     * 节点实例
     */
    private RuActinst ruActinst;

    /**
     * 任务
     */
    private RuTask ruTask;

    /**
     * 用户任务
     */
    private RuTaskUser ruTaskUser;

    /**
     * 当前用户任务判定的走向线ID
     */
    private Long userTaskActinstId;
    /**
     * 流程是否结束
     */
    private Boolean end;

    /**
     * 发起人任务id
     * 为了客户端能使用任务界面临时使用，后面废弃
     */
    @Deprecated
    private String starterUserTaskKey;

    /**
     * 当前节点处理完成后手动抄送人员
     */
    private ChildShapeAssigneeInfo manualInformerInfo;

    private String varJson;

    private List<String> assigneeIds;

    /**
     * 是否多部门
     */
    private Boolean isAllDept;

    /**
     * 当前节点是否必选节点
     */
    private Boolean required;


    /**
     * 外出授权的代理关系
     */
//    private Map<String, AgentUser> agentUserMap;

    /**
     * 表单模型信息
     */
    private ReFormModel reFormModel;

}
