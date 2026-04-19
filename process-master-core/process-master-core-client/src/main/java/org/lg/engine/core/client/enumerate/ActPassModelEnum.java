package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum ActPassModelEnum {

    ONE(1),

    ALL(2),
    ;
    private final Integer mode;

    ActPassModelEnum(Integer mode) {
        this.mode = mode;
    }
}
