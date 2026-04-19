package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class EditSuggestionReq extends BaseWfRequest {
    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;
}
