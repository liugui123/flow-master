package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

/**
 * 查询已经发布的表单模型
 */
@Getter
@Setter
public class ReFormLatestModelListRequest extends BaseWfRequest {
    /**
     * 名称
     */
    private String formModelName;

    /**
     * 应用key
     */
    private String appKey;

    /**
     * 模型key
     */
    private String modelKey;


}
