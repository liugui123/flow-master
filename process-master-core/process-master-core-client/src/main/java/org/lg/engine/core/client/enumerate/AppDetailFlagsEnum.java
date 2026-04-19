package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum AppDetailFlagsEnum {

    DRAFT(1, "草稿"),

    PUBLISHED(2, "已发布"),

    APP_GROUP(4, "应用分组"),

    PROCESS(8, "流程"),

    FORM(16, "表单"),

    PROCESS_FORM(32,"流程表单"),

    SERVICE(64,"外部接口"),

    HTML(128,"静态url页面"),

    ;
    private final Integer flag;
    private final String desc;

    AppDetailFlagsEnum(Integer flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public boolean hasTag(Integer flag) {
        if (flag == null) {
            return false;
        }
        return (this.flag & flag) != 0;
    }
}
