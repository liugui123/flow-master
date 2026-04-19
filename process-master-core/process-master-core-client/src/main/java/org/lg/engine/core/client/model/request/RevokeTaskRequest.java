
package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class RevokeTaskRequest extends BaseWfRequest {

    
    @NotNull(message = "用户任务ID不能为空")
    private String userTaskKey;
}
