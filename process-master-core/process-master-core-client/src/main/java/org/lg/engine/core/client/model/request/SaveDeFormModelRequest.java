package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class SaveDeFormModelRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单标识
     */
    @NotNull(message = "表单模型标识不能为空")
    private String formModelKey;

    /**
     * 表单名称
     */
    @NotNull(message = "表单模型名称不能为空")
    private String formModelName;

    /**
     * 状态 0
     */
    private Integer status = 0;

    /**
     * 0
     */
    private Integer flags = 0;

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
    @NotNull(message = "接入方身份信息不能为空")
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
