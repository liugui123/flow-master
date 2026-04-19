package org.lg.upone.form.model.res;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class DraftFormRes extends App {

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * json数据
     */
    private String formJson;

    /**
     * 表单key
     */
    private String formKey;

    /**
     * 名称
     */
    private String formName;
}
