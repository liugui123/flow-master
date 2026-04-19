package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 任务表存放待办/已办
 */
@Getter
@Setter
@ToString
public class HiRuTask {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long gmtCreate;

    /**
     *
     */
    private Long gmtModified;

    /**
     *
     */
    private String taskName;

    /**
     * 任务别名
     */
    private String taskKey;

    /**
     * 流程实例id
     */
    private Long procinstId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 流程节点实例id
     */
    private Long actinstId;

    /**
     * 流程表单key
     */
    private String procFormKey;

    /**
     * 1抄送, 2会签 , 4或签,  8个签,  16前加签, 32后加签, 64自动完成
     */
    private Integer flags;

    /**
     * 运行时的任务id
     */
    private Long taskId;

    /**
     * 任务等级 1 特级 2 急件 3 一般
     */
    private Integer taskLevel;

    /**
     * 任务上的节点配置
     */
    private String varJson;
}