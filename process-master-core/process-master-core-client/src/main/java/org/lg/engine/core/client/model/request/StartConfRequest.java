package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class StartConfRequest extends BaseWfRequest {


    @NotNull(message = "流程编码不能为空")
    protected String procKey;

}
