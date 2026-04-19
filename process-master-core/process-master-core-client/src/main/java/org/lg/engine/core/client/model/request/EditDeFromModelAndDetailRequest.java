package org.lg.engine.core.client.model.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.util.List;


@Getter
@Setter
public class EditDeFromModelAndDetailRequest extends BaseWfRequest {

    @NotNull(message = "模型信息不能为空")
    @Valid
    private SaveDeFormModelRequest deFormModel;


    List<SaveDeFormModelDetailRequest> deFormModelDetails;

    private Boolean publish = false;

    private Boolean sysForm = false;
}
