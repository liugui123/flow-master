package org.lg.engine.core.client.enumerate;

import lombok.Getter;

/**
 * 查找模式
 */
@Getter
public enum SearchModelEnum {
    UP("up"),
    DOWN("down"),
    AROUND("around"),
    ;
    private final String type;

    SearchModelEnum(String type) {
        this.type = type;
    }


}
