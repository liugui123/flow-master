package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 流程实例表
 */
@Getter
@Setter
@ToString
public class HiRuProcinst {
    private Long id;

    private Long gmtCreate;

    private Long gmtModified;

    /**
     * 流程实例名
     */
    private String procName;

    /**
     * 流程实例的别名
     */
    private String procKey;

    /**
     * 流程定义别名
     */
    private String procDeKey;

    /**
     * 状态 0办理中 1 已办理
     */
    private Integer status;

    /**
     * 流程实例的变量
     */
    private String varJson;

    private String procFormUrlApp;

    private String procFormStartUrlApp;

    private String taskTitle;

    private String taskType;

    /**
     * 发起人id
     */
    private String starterId;

    /**
     * 发起人单位id
     */
    private String starterOrgId;

    /**
     * 发起人名字
     */
    private String starterName;

    /**
     * 发起人组织id
     */
    private String starterOrgName;

    /**
     * 发起人部门id
     */
    private String starterDeptId;

    /**
     * 发起人部门名字
     */
    private String starterDeptName;

    /**
     * 1 正常通过 2 已拒绝 4 终止 8 已撤回
     */
    private Integer flags;

    /**
     * 父流程实例id
     */
    private Long pid;

    /**
     * 流程表单数据标识
     */
    private String procFormDataKey;

    /**
     * 流程表单key
     */
    private String procFormKey;

    /**
     * 用户任务表单跳转链接，打开用户任务的链接
     */
    private String procFormUrl;

    /**
     * 流程发布版本id
     */
    private String procReId;

    /**
     * 流程实例id
     */
    private Long procinstId;

    /**
     * 流程所属的应用id
     */
    private String appKey;

    /**
     * 租户 默认流程引擎是租户，后面根据需要改成其他租户
     */
    private String tenant;

    /**
     * 三方身份标识
     */
    private String bizId;
}