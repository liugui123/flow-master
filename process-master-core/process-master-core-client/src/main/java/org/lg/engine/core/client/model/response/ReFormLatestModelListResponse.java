package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

import java.util.List;


@Getter
@Setter
public class ReFormLatestModelListResponse extends BaseWfResponse {

    private Long id;

    /**
     * 接入标识
     */
    private String formModelKey;

    /**
     * 接入名称
     */
    private String formModelName;

    /**
     * web端待办地址
     */
    private String formModelUrl;

    /**
     * web端启动地址
     */
    private String formModelStartUrl;
    /**
     * 应用端待办地址
     */
    private String formModelUrlApp;
    /**
     * 应用端启动地址
     */
    private String formModelStartUrlApp;

    /**
     * 模型key
     */
    private String modelKey;
    /**
     * 三方身份
     */
    private String bizId;

    /**
     * 表单参数明细
     */
    private List<ReFormModelDetailResponse> details;

    //任务相关配置，线标记废弃
    private String taskTitle;

    private String taskType;
}
