package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class DeServiceModelDetailInsertRequest extends BaseWfRequest {

    /**
     *
     */
    private Integer status = 0;

    /**
     * 0 人员接口 1 条件接口
     */
    private Integer flags = 0;

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
