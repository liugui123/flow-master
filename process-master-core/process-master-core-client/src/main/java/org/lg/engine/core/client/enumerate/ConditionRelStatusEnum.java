package org.lg.engine.core.client.enumerate;

import lombok.Getter;

@Getter
public enum ConditionRelStatusEnum {


    AND("and"),

    OR("or");

    String status;

    ConditionRelStatusEnum(String status) {
        this.status = status;
    }
}
