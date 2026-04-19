package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class CompleteTaskResponse extends BaseWfResponse {

    private Boolean end;
}
