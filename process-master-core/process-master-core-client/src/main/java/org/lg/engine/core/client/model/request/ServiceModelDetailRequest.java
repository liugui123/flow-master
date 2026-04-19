package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class ServiceModelDetailRequest extends BaseWfRequest {
    /**
     * key
     */
    private String serviceModelKey;
}
