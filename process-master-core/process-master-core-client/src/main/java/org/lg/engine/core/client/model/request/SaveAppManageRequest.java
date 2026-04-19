package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.enumerate.AppDetailFlagsEnum;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class SaveAppManageRequest extends BaseWfRequest {

    private String appDetailKey;

    @NotNull(message = "应用分类不能为空")
    private Integer flags = AppDetailFlagsEnum.PROCESS.getFlag();

    @NotNull(message = "父节点不能为空")
    private String parentAppDetailKey;

    private String detailKey;

    @NotNull(message = "详情名字不能为空")
    private String detailName;

    @NotNull(message = "应用Key不能为空")
    private String appKey;

    private String extra;

    private String iconUrl;

}
