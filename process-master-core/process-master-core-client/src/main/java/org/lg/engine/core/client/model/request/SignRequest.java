
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
public class SignRequest extends BaseWfRequest {


    @NotNull(message = "用户任务ID不能为空")
    private String userTaskKey;


    @NotNull(message = "加签动作不能为空")
    private Integer flags;

    @NotNull(message = "加签处理人不能为空")
    @Valid
    private Collection<Assignee> assignees;
}
