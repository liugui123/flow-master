package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单模型信息
 */
@Getter
@Setter
public class DeFormModel {
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
     * 表单标识
     */
    private String formModelKey;

    /**
     * 表单名称
     */
    private String formModelName;

    /**
     * 状态 0
     */
    private Integer status;

    /**
     * 0
     */
    private Integer flags;

    /**
     * 模型key
     */
    private String modelKey;

    /**
     * 表单详情跳转链接，跳转到手写表单详情链接，一般是待办打开链接
     */
    private String formModelUrl;

    /**
     * 表单模型启动链接，跳转到手写表单启动流程链接
     */
    private String formModelStartUrl;

    /**
     * 接入方身份信息
     */
    private String bizId;

    /**
     * 表单模型手机端跳转链接
     */
    private String formModelUrlApp;

    /**
     * 表单模型手机端启动链接
     */
    private String formModelStartUrlApp;

    /**
     *
     */
    private String taskTitle;

    /**
     *
     */
    private String taskType;

    /**
     * 应用key
     */
    private String appKey;
}