package org.lg.engine.core.client.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.Sort;

import java.io.Serializable;
import java.util.Collection;


@Getter
@Setter
@ToString
public class ManualCompleteInfo extends Sort implements Serializable {

    /**
     * 任务处理人
     */
    @NotNull(message = "任务处理人不能为空")
    private Collection<Assignee> assignees;

    @NotNull(message = "处理节点不能为空")
    private String actKey;

}
