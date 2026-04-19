package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum ProcInstStatusEnum {

    RUNNING(0, "运行中"),

    FINISH(1, "正常通过"),

    REFUSE(2, "已拒绝"),

    ABORTED(4, "终止"),

    REVOKE(8, "已撤销"),

    CANCEL(16, "已注销"),
    ;

    private final Integer status;

    private final String desc;

    ProcInstStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }


    public static String getValueByFlags(Integer flags) {
        for (ProcInstStatusEnum platformFree : ProcInstStatusEnum.values()) {
            if (flags.equals(platformFree.getStatus())) {
                return platformFree.getDesc();
            }
        }
        return null;
    }

}
