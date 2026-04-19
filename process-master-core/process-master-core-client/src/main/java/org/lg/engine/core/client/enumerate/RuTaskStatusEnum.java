package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum RuTaskStatusEnum {
    /**
     * 待处理
     */
    PENDING(0),
    /**
     * 已完成
     */
    DONE(1);


    private final Integer status;

    RuTaskStatusEnum(int status) {
        this.status = status;
    }
}
