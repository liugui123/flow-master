
package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class UserTaskDeliverRequest extends BaseWfRequest {


    @NotNull(message = "转交人不能为空")
    @Valid
    Assignee assignee;


    @NotNull(message = "用户任务ID不能为空")
    private String userTaskKey;


    private String optionDesc = "用户任务转交";
}
