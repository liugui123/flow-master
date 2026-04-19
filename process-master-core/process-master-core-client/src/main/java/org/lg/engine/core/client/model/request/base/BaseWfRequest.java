package org.lg.engine.core.client.model.request.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;

import java.io.Serializable;


@Getter
@Setter
public class BaseWfRequest implements Serializable {
    /**
     * 应用
     */
    private String appKey;
    /**
     * 操作人
     */
    @NotNull(message = "操作人不能为空")
    @Valid
    protected Operator operator;
    
    protected Integer userTaskLevel;

    protected String comment;

    protected String varJson;
}
