package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.util.List;


@Getter
@Setter
public class PublishDeFromModelAndDetailRequest extends BaseWfRequest {

    @NotNull(message = "模型信息不能为空")
    @Valid
    private PublishDeFormModelRequest deFormModelReq;


    List<PublishDeFormModelDetailRequest> deFormModelDetailsReq;

}
