package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

/**
 * liugui
 * @Date 2022/4/8
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CheckProcessStartStrategyRequest extends BaseWfRequest {


    /**
     * 流程key
     */
    @NotBlank(message = "流程定义key不能为空")
    private String procDeKey;
}
