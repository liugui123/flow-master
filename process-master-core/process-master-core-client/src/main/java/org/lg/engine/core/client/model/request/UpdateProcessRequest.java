package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class UpdateProcessRequest extends BaseWfRequest {

    @NotNull(message = "状态不能为空")
    private Integer status;

    
    protected Integer flags;

    
    protected String procKey;
    
    @NotNull(message = "流程定义名称不能为空")
    protected String procName;

    
    @NotNull(message = "流程json数据不能为空")
    protected String procJson;

    
    protected String procViewJson;

    
    protected String procConfJson;

    
    protected Integer procVersion;

    /**
     * 表单模型id，标识流程实用的表单模型是哪个
     */
    protected Long formModelReId;

}
