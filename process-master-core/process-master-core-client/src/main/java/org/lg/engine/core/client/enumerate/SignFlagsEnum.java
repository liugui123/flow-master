package org.lg.engine.core.client.enumerate;

import lombok.Getter;

@Getter
public enum SignFlagsEnum {

    /**
     * 在我之前加签
     */
    BEFORE_ME(0),
    /**
     * 在我之后加签
     */
    AFTER_ME(1);

    Integer flag;

    SignFlagsEnum(Integer flags) {
        this.flag = flags;
    }

}
