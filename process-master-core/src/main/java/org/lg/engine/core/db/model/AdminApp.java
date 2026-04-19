package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 应用
 */
@Getter
@Setter
@ToString
public class AdminApp {
    private Long id;

    private Long gmtCreate;

    private Long gmtModified;

    private String appName;

    private String appDesc;

    private String appIcon;

    /**
     * 0 存在 1 删除
     */
    private Integer status;

    private Integer flags;

    /**
     * 创建人
     */
    private String creatorId;

    private String creatorName;

    private String creatorDeptId;

    private String creatorDeptName;

    /**
     * 创建人组织
     */
    private String creatorOrgId;

    private String creatorOrgName;

    /**
     * 应用标识
     */
    private String appKey;

    private Integer sortScore;
}
