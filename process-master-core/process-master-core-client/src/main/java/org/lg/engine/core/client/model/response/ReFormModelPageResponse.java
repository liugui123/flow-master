package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class ReFormModelPageResponse extends BaseWfResponse {

    private Long id;

    /**
     * 状态 0
     */
    private Integer status;

    /**
     * 接入标识
     */
    private String formModelKey;

    /**
     * 接入名称
     */
    private String formModelName;

    /**
     * 三方身份
     */
    private String bizId;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 模型版本
     */
    private Integer modelVersion;

}
