package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;


@Getter
@Setter
public class DeFormModelListRequest extends BaseWfRequest {

    private String formModelName;

    private String formModelKey;

    private String appKey;

}
