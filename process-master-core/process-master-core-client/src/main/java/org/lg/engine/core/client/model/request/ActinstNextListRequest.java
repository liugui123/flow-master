package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class ActinstNextListRequest extends BaseWfRequest {

    @NotNull(message = "任务ID不能为空")
    private String userTaskKey;


    private String varJson;
}
