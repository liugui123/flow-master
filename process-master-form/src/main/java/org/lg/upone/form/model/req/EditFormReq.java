package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;
@Getter
@Setter
public class EditFormReq extends App{
    /**
     * 标识
     */
    private String formKey;

    /**
     * json数据
     */
    private String formJson;

    /**
     * 名称
     */
    private String formName;

    /**
     * 是否发布
     */
    private boolean publish;
}
