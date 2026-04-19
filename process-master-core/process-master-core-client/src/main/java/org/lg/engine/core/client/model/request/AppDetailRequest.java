package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class AppDetailRequest extends BaseWfRequest {
    /**
     * 名字
     */
    private String name;
    /**
     * 应用key
     */
    @NotNull(message = "应用id不能为空")
    private String appKey;

    /**
     * 分组的id
     */
    @NotNull(message = "组id不能为空")
    private String groupAppDetailKey;

    /**
     * 查询详情标记
     */
    private Integer flags;


    private Boolean needPermission = false;

    private Integer status;
}
