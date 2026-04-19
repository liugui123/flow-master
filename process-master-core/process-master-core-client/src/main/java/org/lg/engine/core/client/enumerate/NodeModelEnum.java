package org.lg.engine.core.client.enumerate;

import lombok.Getter;

/**
 * 节点运行模式
 */
@Getter
public enum NodeModelEnum {

    AUTO("auto", "自动执行"),

    MAN("man", "人工执行"),

    ;
    private final String code;
    private final String desc;

    NodeModelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
