package org.lg.engine.core.client.enumerate;


import lombok.Getter;

@Getter
public enum ProcInstFlagsEnum {

    DEFAULT(0, "默认"),
    ;

    private final Integer flags;

    private final String desc;

    ProcInstFlagsEnum(int flags, String desc) {
        this.flags = flags;
        this.desc = desc;
    }


    public static String getValueByFlags(Integer flags) {
        for (ProcInstFlagsEnum platformFree : ProcInstFlagsEnum.values()) {
            if (flags.equals(platformFree.getFlags())) {
                return platformFree.getDesc();
            }
        }
        return null;
    }

}
