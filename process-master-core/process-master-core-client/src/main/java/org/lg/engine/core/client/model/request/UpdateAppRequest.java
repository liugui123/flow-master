package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class UpdateAppRequest extends BaseWfRequest {


    @NotNull(message = "应用id不能为空")
    private String appKey;

    
    @NotNull(message = "应用名称不能为空")
    private String appName;

    
    private String appIcon;

    
    private String appDesc;

}
