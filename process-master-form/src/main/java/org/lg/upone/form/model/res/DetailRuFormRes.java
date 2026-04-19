package org.lg.upone.form.model.res;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class DetailRuFormRes extends App {

    /**
     * 表单结构json数据
     */
    private String formConfJson;

    /**
     * 表单数据json数据
     */
    private String formDataJson;
}
