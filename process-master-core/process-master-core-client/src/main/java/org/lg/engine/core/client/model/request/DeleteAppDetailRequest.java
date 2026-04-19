package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class DeleteAppDetailRequest extends BaseWfRequest {

    @NotNull(message = "ID不能为空")
    private String appDetailKey;
}
