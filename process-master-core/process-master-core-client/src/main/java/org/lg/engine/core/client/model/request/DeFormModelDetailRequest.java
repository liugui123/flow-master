package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


public class DeFormModelDetailRequest extends BaseWfRequest {


    @NotNull(message = "草稿表单ID不能为空")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
