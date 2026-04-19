package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Assignee;

import java.io.Serializable;
import java.util.Collection;


@Getter
@Setter
public class ManualStartInfo implements Serializable {

    @NotNull(message = "手动启动传递的节点不能为空")
    private String actKey;

    private Collection<Assignee> assignees;
}
