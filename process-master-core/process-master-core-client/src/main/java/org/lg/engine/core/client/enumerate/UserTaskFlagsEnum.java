package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum UserTaskFlagsEnum {


    NONE(0, "无"),

    COPY(1, "抄送"),

    COUNTERSIGN(2, "会签"),

    OR_SIGN(4, "或签"),

    INDIVIDUAL_SIGN(8, "个签"),

    BEFORE_SIGN(16, "前加签"),

    AFTER_SIGN(32, "后加签"),

    MY_START(64, "发起节点"),

    AGENT_TASK(128, "代理任务"),

    READ_TASK(256, "分发任务"),

    ;
    private final Integer flag;
    private final String desc;

    UserTaskFlagsEnum(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public boolean hasTag(int flag) {
        return (this.flag & flag) != 0;
    }

    public static UserTaskFlagsEnum getByFlag(int flag) {
        for (UserTaskFlagsEnum value : UserTaskFlagsEnum.values()) {
            if (value.getFlag().equals(flag)) {
                return value;
            }
        }
        return null;
    }
}
