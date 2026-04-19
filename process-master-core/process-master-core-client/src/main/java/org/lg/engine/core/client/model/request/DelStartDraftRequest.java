
package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class DelStartDraftRequest extends BaseWfRequest {

    /**
     * 表单数据标识
     */
    @NotNull(message = "表单数据标识不能为空")
    private String procFormDataKey;
}
