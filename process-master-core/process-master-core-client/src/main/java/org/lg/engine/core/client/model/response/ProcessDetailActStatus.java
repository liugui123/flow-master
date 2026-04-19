package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Getter
@Setter
public class ProcessDetailActStatus extends BaseWfResponse {


    private String actinstKey;


    private Integer status;
}
