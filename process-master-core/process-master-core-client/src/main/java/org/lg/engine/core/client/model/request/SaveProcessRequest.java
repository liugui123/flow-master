package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class SaveProcessRequest extends BaseWfRequest {

    /**
     * 为1代表需要发布流程
     */
    private Integer status;

    /**
     * 默认0，无实际用途
     */
    protected Integer flags = 0;

    /**
     * 流程标识
     */
    protected String procKey;
    /**
     * 流程名称
     */
    @NotNull(message = "流程定义名称不能为空")
    protected String procName;

    /**
     * 流程配置信息
     */
    protected String procJson = "{}";

    /**
     * 视图json
     */
    protected String procViewJson;

    /**
     * 流程表单标识
     */
    private String procFormKey;


    protected String procConfJson;


    protected Integer procVersion;

    /**
     * 所属应用标识
     */
    private String appKey;


    public SaveProcessRequest() {
    }

    public SaveProcessRequest(String procKey,
                              String procName,
                              String procJson,
                              String procViewJson,
                              String procFormKey,
                              String appKey) {
        this.procKey = procKey;
        this.procName = procName;
        this.procJson = procJson;
        this.procViewJson = procViewJson;
        this.procFormKey = procFormKey;
        this.appKey= appKey;
    }
}
