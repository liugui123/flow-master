package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class ReFormModelDetailByKeyRequest extends BaseWfRequest {
    
    @NotNull(message = "表单模型key不能为空")
    private String formModelKey;
}
