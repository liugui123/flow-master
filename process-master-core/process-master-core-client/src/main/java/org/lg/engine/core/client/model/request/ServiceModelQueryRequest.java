package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class ServiceModelQueryRequest extends BaseWfRequest {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String appKey;

    /**
     * 详情名字
     */
    private String name;


    /**
     * 接口类型
     */
    private Integer flags;
}
