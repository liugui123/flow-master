package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Getter
@Setter
public class ServiceModelDetailResponse extends BaseWfResponse {

    /**
     *
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long gmtCreate = System.currentTimeMillis();

    /**
     * 修改时间
     */
    private Long gmtModified = System.currentTimeMillis();

    /**
     * 外部接口id
     */
    private Long serviceId;

    /**
     * 字段名字
     */
    private String fieldName;

    /**
     * 字段标识
     */
    private String fieldKey;

    /**
     * 字段组件类型
     */
    private String fieldCptType;

}
