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
public class RuProcinst {
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
     * 流程发布版本id
     */
    private Long procReId;

    /**
     * 状态 0办理中 1 已办理
     */
    private Integer status;

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
     * 0 默认
     */
    private Integer flags;

    /**
     * 租户 默认流程引擎是租户，后面根据需要改成其他租户
     */
    private String tenant;

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
     * 流程实例来源，说明是具体的流程发起方，流程用来反馈给具体发起方信息
     */
    private String bizId;

    /**
     * 流程所属的应用id
     */
    private String appKey;

    private String varJson;

    private String procFormUrlApp;

    private String procFormStartUrlApp;

    private String taskTitle;

    private String taskType;

    /**
     * 流程图视图json
     */
    private String procViewJson;

    /**
     * 流程图视图json
     */
    private String procJson;
}
