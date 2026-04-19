package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

/**
 * 查询已经发布的表单模型
 */
@Getter
@Setter
public class ReServiceModelDisableEnableRequest extends BaseWfRequest {
    private Long id;
    /**
     * 状态
     * 停用/启用
     */
    private Integer status;



}
