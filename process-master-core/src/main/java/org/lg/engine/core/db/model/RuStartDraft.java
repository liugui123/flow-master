package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 我的启动草稿表
 */
@Getter
@Setter
@ToString
public class RuStartDraft {
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

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
     * 启动的流程key
     */
    private String procKey;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private Integer flags;

    /**
     * 所属应用id，即为app表中的主键id
     */
    private String appKey;

    /**
     * 表单数据标识
     */
    private String procFormDataKey;

    /**
     * 流程表单key
     */
    private String procFormKey;

    /**
     * 草稿标题
     */
    private String draftTitle;

    /**
     * 手机端启动跳转地址
     */
    private String formModelStartUrlApp;

    /**
     * 表单模型启动链接，跳转到手写表单启动流程链接
     */
    private String formModelStartUrl;
}