package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户和任务关系表
 */
@Getter
@Setter
@ToString
public class HiRuTaskUser {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户任务标识
     */
    private String userTaskKey;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     * 用户任务结束时间
     */
    private Long gmtEnd;

    /**
     * 用户id
     */
    private String assigneeId;

    /**
     * 用户名称
     */
    private String assigneeName;

    /**
     * 处理人部门名称
     */
    private String assigneeDeptId;

    /**
     * 用户部门名称
     */
    private String assigneeDeptName;

    /**
     * 用户组织
     */
    private String assigneeOrgId;

    /**
     * 用户组织名称
     */
    private String assigneeOrgName;

    /**
     * 处理人手机号
     */
    private String assigneeMobile;

    /**
     * 任务主键
     */
    private Long taskId;

    /**
     * 流程实例id
     */
    private Long procinstId;

    /**
     * 0待办，1已办
     */
    private Integer status;

    /**
     * 1抄送, 2会签 , 4或签,  8个签,  16前加签, 32后加签, 64自动完成，128已拒绝，256已撤回，512已转交，1024正常通过,2048我发起的（）
     */
    private Integer flags;

    /**
     * 任务描述 动作描述：\n 发起 通过  拒绝  待处理  退回  终止流程 加签  转交 撤回 自动同意
     */
    private String taskDesc;

    /**
     * 人员意见
     */
    private String comment;

    /**
     * 用户任务父节点
     */
    private Long pid;

    /**
     * 表单数据标识
     */
    private String procFormDataKey;

    /**
     * 流程表单key
     */
    private String procFormKey;

    /**
     * 流程调用方
     */
    private String bizId;

    /**
     * 用户任务id
     */
    private Long userTaskId;

    /**
     * 所属应用id
     */
    private String appKey;

    /**
     * 用户任务表单跳转链接，打开用户任务的链接
     */
    private String procFormUrl;

    /**
     * 用户任务等级
     */
    private Integer userTaskLevel;

    private String taskTitle;

    private String taskType;

    private String procFormUrlApp;
}