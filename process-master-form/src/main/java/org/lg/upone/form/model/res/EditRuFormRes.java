package org.lg.upone.form.model.res;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class EditRuFormRes extends App {

    /**
     * 表单保存数据时产生数据key
     */
    private String formDataKey;
}
