package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 流程定义表
 */
@Getter
@Setter
@ToString
public class ReProcess {
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
     * 0草稿 1发布并启用2停用
     */
    private Integer status;

    /**
     * 1系统流程 2 单位流程
     */
    private Integer flags;

    /**
     * 流程图json数据
     */
    private String procJson;

    /**
     * 流程图视图json
     */
    private String procViewJson;

    /**
     * 版本号
     */
    private Integer procVersion;

    /**
     * 流程别名
     */
    private String procKey;

    /**
     * 流程名称
     */
    private String procName;

    /**
     * 流程定义id
     */
    private Long procDeId;

    /**
     * 待办业务类型
     */
    private Integer businessType;

    /**
     * 表单模型id，标识流程实用的表单模型是哪个
     */
    private Long formModelReId;

    /**
     * 所属应用标识
     */
    private String appKey;
}