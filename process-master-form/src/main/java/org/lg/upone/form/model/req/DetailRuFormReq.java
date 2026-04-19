package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;
@Getter
@Setter
public class DetailRuFormReq extends App{
    /**
     * 数据表单标识
     */
    private String formDataKey;

}
