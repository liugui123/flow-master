package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * liugui
 * @Date 2022/3/7
 */
@Getter
@Setter
public class ManualBackRequest extends BackRequest{

    @NotEmpty(message = "需要退回到的节点实例ID不能为空")
    private Long actinstId;
}
