package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.util.List;


@Getter
@Setter
public class ProcessListRequest extends BaseWfRequest {


    @NotNull(message = "流程状态不能为空")
    protected Integer status;


    @NotNull(message = "流程标识不能为空")
    private List<String> procKeys;

}
