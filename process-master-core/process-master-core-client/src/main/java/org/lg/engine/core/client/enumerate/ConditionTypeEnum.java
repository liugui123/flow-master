package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum ConditionTypeEnum {

    /**
     * 替补条件
     */
    ELSE(2),
    /**
     * 自定义
     */
    CUSTOM(1),
    /**
     * 默认
     */
    DEFAULT(0),
    ;
    private final Integer type;

    ConditionTypeEnum(Integer type) {
        this.type = type;
    }
}
