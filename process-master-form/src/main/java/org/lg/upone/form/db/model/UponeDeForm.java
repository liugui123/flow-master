package org.lg.upone.form.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单定义表
 */
@Getter
@Setter
public class UponeDeForm {
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
     * 0默认
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
     * 表单key
     */
    private String formKey;

    /**
     * 名称
     */
    private String formName;

    /**
     * 应用
     */
    private String appKey;
}