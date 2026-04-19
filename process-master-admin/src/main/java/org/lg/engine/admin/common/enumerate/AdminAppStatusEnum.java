package org.lg.engine.admin.common.enumerate;

import lombok.Getter;

/**
 * 应用状态
 */
@Getter
public enum AdminAppStatusEnum {

    EXIST(0, "存在"),
    DELETE(1, "删除"),
    ;
    private final Integer code;
    private final String desc;

    AdminAppStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
