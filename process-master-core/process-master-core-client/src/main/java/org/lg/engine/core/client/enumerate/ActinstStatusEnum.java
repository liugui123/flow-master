package org.lg.engine.core.client.enumerate;

import lombok.Getter;

/**
 * 流程实例状态 0运行中 1已完成 2 待处理
 */
@Getter
public enum ActinstStatusEnum {

    /**
     * 运行
     */
    RUNNING(0),

    /**
     * 完成
     */
    FINISHED(1),

    /**
     * 待处理
     */
    PENDING(2);

    private final Integer code;

    ActinstStatusEnum(Integer code) {
        this.code = code;
    }
}
