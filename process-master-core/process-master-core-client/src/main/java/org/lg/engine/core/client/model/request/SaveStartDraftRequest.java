
package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class SaveStartDraftRequest extends BaseWfRequest {

    /**
     * 启动的流程key
     */
    @NotNull(message = "启动的流程key不能为空")
    private String procKey;

    /**
     * 表单数据标识
     */
    @NotNull(message = "表单数据标识不能为空")
    private String procFormDataKey;

    /**
     * 草稿标题
     */
    private String draftTitle;
}
