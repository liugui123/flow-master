package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用详情
 */
@Setter
@Getter
public class DeServiceModel {
    /**
     *
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     *
     */
    private Integer status;

    /**
     * 0 人员接口 1 条件接口
     */
    private Integer flags;

    /**
     * 业务方的填入注册表单标识，不能重复
     */
    private String serviceModelKey;

    /**
     * 名字
     */
    private String name;

    /**
     * 三方查询接口
     */
    private String url;

    /**
     * 应用key
     */
    private String appKey;

    /**
     * 和id对应
     */
    private String modelKey;

}