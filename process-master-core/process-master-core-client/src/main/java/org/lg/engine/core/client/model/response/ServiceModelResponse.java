package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

import java.util.List;

@Getter
@Setter
public class ServiceModelResponse extends BaseWfResponse {

    /**
     *
     */
    private Long id;

    /**
     * 详情key
     */
    private String key;

    /**
     * 详情名字
     */
    private String name;

    /**
     * 三方查询接口
     */
    private String url;

    /**
     * 0 人员接口 1 条件接口
     */
    private Integer flags;

    /**
     * 明细
     */
    List<ServiceModelDetailResponse> detailResponses;

}
