package org.lg.upone.form.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程定义表
 */
@Getter
@Setter
public class UponeReForm {
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
     *
     */
    private Integer flags;

    /**
     * json数据
     */
    private String formJson;

    /**
     * 版本号
     */
    private Integer formVersion;

    /**
     * 别名
     */
    private String formKey;

    /**
     * 名称
     */
    private String formName;

    /**
     * 定义id
     */
    private Long formDeId;

    /**
     * 应用
     */
    private String appKey;
}