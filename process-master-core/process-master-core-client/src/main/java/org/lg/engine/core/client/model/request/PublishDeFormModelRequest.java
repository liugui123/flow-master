package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * liugui
 * @date 2021/10/29 16:38
 **/
@Getter
@Setter
public class PublishDeFormModelRequest {
    private Long id;

    @NotNull(message = "表单模型标识不能为空")
    private String formModelKey;

    @NotNull(message = "表单模型名称不能为空")
    private String formModelName;


    @NotNull(message = "接入方身份信息不能为空")
    private String bizId;


    private String formModelStartUrl;


    private String formModelUrl;
    @NotNull(message = "模型标识不能为空")
    private String modelKey;

    /**
     * 表单模型手机端跳转链接
     */
    private String formModelUrlApp;

    /**
     * 表单模型手机端启动链接
     */
    private String formModelStartUrlApp;

    private String taskTitle;

    private String taskType;

    /**
     * 应用key
     */
    private String appKey;

    /**
     * 0
     */
    private Integer flags;
}
