
package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
@ToString
public class PublishProcessDefRequest extends BaseWfRequest {


    @NotNull(message = "流程定义标识不能为空")
    String defProcessKey;
}
