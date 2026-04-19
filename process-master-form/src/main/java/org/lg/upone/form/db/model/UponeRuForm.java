package org.lg.upone.form.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 运行时低代码表单存储数据的地方
 */
@Getter
@Setter
public class UponeRuForm {
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
    private Integer status;

    /**
     *
     */
    private Integer flags;

    /**
     * 表单key
     */
    private String formKey;

    /**
     * 表单保存数据时产生数据key
     */
    private String formDataKey;

    /**
     * 表单结构json数据
     */
    private String formConfJson;

    /**
     * 表单数据json数据
     */
    private String formDataJson;

    /**
     * 应用标识
     */
    private String appKey;
}