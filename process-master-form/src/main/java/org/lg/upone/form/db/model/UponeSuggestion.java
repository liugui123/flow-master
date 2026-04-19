package org.lg.upone.form.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 功能反馈表
 */
@Getter
@Setter
public class UponeSuggestion {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long gmtCreate = System.currentTimeMillis();

    /**
     *
     */
    private Long gmtModified = System.currentTimeMillis();

    /**
     * 0默认
     */
    private Integer status = 0;

    /**
     * 0默认
     */
    private Integer flags = 0;

    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 操作人名字
     */
    private String operatorName;

    /**
     * 操作人组织id
     */
    private String operatorOrgId;

    /**
     * 操作人组织名称
     */
    private String operatorOrgName;

    /**
     * 操作人部门id
     */
    private String operatorDeptId;

    /**
     * 操作人部门名字
     */
    private String operatorDeptName;
}