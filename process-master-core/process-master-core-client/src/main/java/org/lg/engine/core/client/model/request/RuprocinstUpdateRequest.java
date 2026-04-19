package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class RuprocinstUpdateRequest extends BaseWfRequest {

    @NotNull(message = "流程实例ID不能为空")
    protected String procKey;

    /**
     * 流程图json数据
     */
    @NotNull(message = "流程图数据不能为空")
    private String procJson;

    /**
     * 流程图视图json
     */
    @NotNull(message = "流程图视图数据不能为空")
    private String procViewJson;

    /*
     * 跳跃的节点key
     */
    private String jumpResourceId;

}
