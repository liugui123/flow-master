package org.lg.upone.form.model.req;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;
@Getter
@Setter
public class EditRuFormReq extends App{
    /**
     * 表单key
     */
    private String formKey;

    /**
     * 表单保存数据时产生数据key
     */
    private String formDataKey;

    /**
     * 表单结构json数据
     */
    private String formConfJson;

    /**
     * 表单数据json数据
     */
    private String formDataJson;
}
