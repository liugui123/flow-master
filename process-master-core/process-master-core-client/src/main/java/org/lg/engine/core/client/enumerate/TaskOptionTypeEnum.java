package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum TaskOptionTypeEnum {

    START_PROC("number", "发起"),

    PASS("string", "通过"),

    REFUSE("refuse", "拒绝"),

    BACK("back", "退回"),

    TERMINATION("termination", "终止流程"),

    SIGN("sign", "加签"),

    REVOKE("revoke", "撤回"),


    CREATE_TASK("createTask", "创建任务"),
    ;
    private final String code;
    private final String desc;

    TaskOptionTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
