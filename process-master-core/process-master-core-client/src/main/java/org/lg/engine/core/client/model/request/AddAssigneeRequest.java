
package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.util.Collection;

@Getter
@Setter
public class AddAssigneeRequest extends BaseWfRequest {

    
    @NotNull(message = "转交人不能为空")
    @Valid
    Collection<Assignee> assignees;

    @NotNull(message = "用户任务ID不能为空")
    private String userTaskKey;

}
