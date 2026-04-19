package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.utils.Query;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class DetailFormReq extends App {
    /**
     * 名称
     */
    private String formKey;

}
