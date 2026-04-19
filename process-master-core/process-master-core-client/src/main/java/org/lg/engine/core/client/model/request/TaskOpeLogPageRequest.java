
package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.utils.Query;

@Getter
@Setter
@ToString
public class TaskOpeLogPageRequest extends Query {

    @NotNull(message = "操作人不能为空")
    @Valid
    protected Operator operator;

    private Long minGmtCreate;

    private Long maxGmtCreate;
}
