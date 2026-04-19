package org.lg.upone.form.model.res;

import lombok.Getter;
import lombok.Setter;
import org.lg.upone.form.model.App;

@Getter
@Setter
public class PublishFormRes extends App {

    /**
     *
     */
    private Long gmtCreate;

    /**
     * 0草稿 1发布并启用2停用
     */
    private Integer status;

    /**
     * 版本号
     */
    private Integer formVersion;

    /**
     * 别名
     */
    private String formKey;

    /**
     * 名称
     */
    private String formName;
}
