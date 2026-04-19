package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.utils.Query;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class ReFormCopyReq extends App {
    /**
     * 表单标识
     */
    private String formKey;
}
