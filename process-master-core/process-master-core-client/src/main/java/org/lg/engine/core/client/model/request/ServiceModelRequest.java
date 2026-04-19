package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.util.List;

@Getter
@Setter
public class ServiceModelRequest extends BaseWfRequest {
    /**
     * key
     */
    private String modelKey;

    /**
     * 业务方的填入注册表单标识，不能重复
     */
    private String serviceModelKey;

    /**
     *
     */
    private String appKey;

    /**
     * 详情名字
     */
    private String name;

    /**
     * 三方查询接口
     */
    private String url;

    /**
     * 接口类型
     */
    private Integer flags = 0;

    /**
     * 参数明细
     */
    private List<DeServiceModelDetailInsertRequest> detailInsertRequest;

    /**
     * 是否发布
     */
    boolean publish = false;
}
