
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.enumerate.ActPassModelEnum;
import org.lg.engine.core.client.enumerate.NodeModelEnum;

import java.util.List;

@Getter
@Setter
public class ChildShape {
    /**
     * 节点标识
     */
    private String resourceId;
    /**
     * 节点属性
     */
    private ChildShapeProperties properties;
    /**
     * actType 节点类型
     */
    private String actType;

    /**
     * 节点出线对象
     */
    private List<ChildShapeOutgoing> outgoing;

    /**
     * 任务处理人
     */
    private ChildShapeAssigneeInfo assigneeInfo;

    /**
     * 任务候选人
     */
    private ChildShapeAssigneeInfo noneAssignee;

    /**
     * 任务抄送人
     */
    private ChildShapeAssigneeInfo informerInfo;

    /**
     * 任务加签人
     */
    private ChildShapeAssigneeInfo addSignAssigneeInfo;

    /**
     * 转发人
     */
    private ChildShapeAssigneeInfo forwardAssigneeInfo;

    /**
     * 添加处理人
     */
    private ChildShapeAssigneeInfo addAssigneeInfo;

    /**
     * 多人审批规则 多人审批规则 会签： 2，个签：8， 或签：4
     */
    private Integer multipleAssignee = 4;

    /**
     * 节点上配置的条件
     */
    private ChildShapeCondition condition;

    /**
     * 网关节点通过模型
     * 串行 serial 并行 concurrent
     *
     * @see ActPassModelEnum
     */
    private Integer passMode = ActPassModelEnum.ONE.getMode();

    /**
     * 流程节点意见查看权限
     */
    private CommentPermission commentPermission;

    /**
     * 流程图 配置的回退节点信息
     */
    private List<String> backAbleActKeys;

    /**
     * 是否可以回退直送 false 不可以 true 可以
     */
    private Boolean backOut = false;

    /**
     * 排除已经处理过的人员
     */
    private Boolean filterSelf;

    /**
     * 催办配置
     */
    private ActInstUrgeConfig urgeConfig;

    /**
     * 执行模式，手动选择 自动选择
     *
     * @see NodeModelEnum
     */
    private String nodeMode;

    /**
     * 加签配置
     */
    private SignConfig signConfig;

    /**
     * 是否多部门
     */
    private Boolean isAllDept;

    /**
     * 当前节点是否必选节点
     */
    private Boolean required;

    /**
     * 节点上注册的服务列表
     */
    private List<Service> services;

    /**
     * 表单字段权限
     */
    private List<ChildFormFieldsAuth> formFieldsAuth;

    /**
     * 流程按钮权限
     */
    private List<ChildProcessBtnAuth> processBtnAuth;

}
