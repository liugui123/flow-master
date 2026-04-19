package org.lg.engine.core.client.enumerate;

import lombok.Getter;

import java.util.Arrays;

/**
 * liugui
 * @date 2021/11/23 12:05
 **/
@Getter
public enum /**/ChildShapeConTypeEnum {

    API("api"),

    FLOW("flow"),

    FORM("form"),
    ;
    private final String code;

    ChildShapeConTypeEnum(String code) {
        this.code = code;
    }


    public static ChildShapeConTypeEnum getById(String code) {
        return Arrays.stream(ChildShapeConTypeEnum.values()).filter(v -> v.getCode().equals(code)).findFirst().get();
    }
}
