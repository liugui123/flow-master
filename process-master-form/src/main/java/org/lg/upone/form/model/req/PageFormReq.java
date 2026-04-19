package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.utils.Query;
import org.lg.upone.form.model.App;
@Getter
@Setter
public class PageFormReq extends Query {
    /**
     * 名称
     */
    private String formName;

    /**
     * 是否发布
     */
    private boolean publish;

    private String appKey;
}
