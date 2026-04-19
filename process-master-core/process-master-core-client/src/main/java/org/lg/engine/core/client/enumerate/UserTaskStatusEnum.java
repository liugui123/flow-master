package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum UserTaskStatusEnum {
    /**
     * 待处理
     */
    PENDING(0,"待处理"),
    /**
     * 已处理
     */
    COMMON_FINISH(1,"已处理"),
    /**
     * 分发完成
     */
    READ_FINISHED(2,"分发完成"),
    /**
     * 自动完成
     */
    AUTO_COMPLETE(4,"自动完成"),
    /**
     * "拒绝"
     */
    REFUSE(8,"拒绝"),
    /**
     * 发起人已撤回任务
     */
    REVOKE(16,"发起人已撤回任务"),
    /**
     * 任务已转交
     */
    DELIVERED(32,"任务已转交"),
    /**
     * 正常通过
     */
    PROCESS_CANCEL(64,"流程取消"),
    /**
     * 退回
     */
    BACK(128,"退回"),
    ;
    private final Integer status;

    private final String desc;

    UserTaskStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public boolean hasTag(int status) {
        return (this.status & status) != 0;
    }
}
