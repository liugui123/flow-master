package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class ReServiceModelPageResponse extends BaseWfResponse {

    /**
     *
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long gmtCreate;

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
     *
     */
    private Integer modelVersion;

}
