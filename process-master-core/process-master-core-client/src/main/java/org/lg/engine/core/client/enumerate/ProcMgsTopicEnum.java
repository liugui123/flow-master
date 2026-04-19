package org.lg.engine.core.client.enumerate;

import lombok.Getter;


@Getter
public enum ProcMgsTopicEnum {

    /**
     * 流程启动
     */
    MOA_PROCINST_START("moa_procinst_start"),

    /**
     * 流程结束
     */
    MOA_PROCINST_END("moa_procinst_end"),

    /**
     * 流程出错
     */
    MOA_PROCINST_ERROR("moa_procinst_error"),

    /**
     * 节点任务完成
     */
    MOA_ACTINST_FINISH("moa_actinst_finish"),

    /**
     * 创建用户任务
     */
    MOA_USER_TASK("moa_user_task"),

    /**
     * 流程回退
     */
    MOA_BACK("moa_back"),

    MOA_CANCEL("moa_cancel"),
    ;

    private final String topic;

    ProcMgsTopicEnum(String topic) {
        this.topic = topic;
    }
}
