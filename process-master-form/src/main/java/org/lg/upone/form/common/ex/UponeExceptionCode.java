package org.lg.upone.form.common.ex;

import lombok.Getter;

@Getter
public enum UponeExceptionCode {

    FORM_DE_BLANK(-1001, "表单定义不存在");

    public final java.lang.Integer code;
    public final java.lang.String msg;

    private UponeExceptionCode( Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}
