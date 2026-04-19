package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单模型信息
 */
@Getter
@Setter
public class ReFormModel {
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
     * 表单模型标识
     */
    private String formModelKey;

    /**
     * 表单模型名称
     */
    private String formModelName;

    /**
     * 表单详情跳转链接，跳转到手写表单详情链接，一般是待办打开链接
     */
    private String formModelUrl;

    /**
     * 表单模型启动链接，跳转到手写表单启动流程链接
     */
    private String formModelStartUrl;

    /**
     * 表单发布的来源ID
     */
    private Long deFormId;

    /**
     * 状态 0
     */
    private Integer status;

    /**
     * 0
     */
    private Integer flags;

    /**
     * 当前数据的标识
     */
    private String modelKey;

    /**
     * 模型版本
     */
    private Integer modelVersion;

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
     *
     */
    private String appKey;
}