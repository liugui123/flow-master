package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.utils.Query;

/**
 * 查询已经发布的表单模型
 */
@Getter
@Setter
public class ReServiceModelPageRequest extends Query {

    @NotNull(message = "操作人不能为空")
    @Valid
    protected Operator operator;
    /**
     * 模型key
     */
    @NotNull(message = "模型key不能为空")
    private String modelKey;

    /**
     * 状态
     * 停用/启用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;



}
