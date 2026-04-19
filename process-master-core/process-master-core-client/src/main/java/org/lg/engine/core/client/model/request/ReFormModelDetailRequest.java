package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class ReFormModelDetailRequest extends BaseWfRequest {

    @NotNull(message = "发布表单ID不能为空")
    private Long reFormModelId;
}
