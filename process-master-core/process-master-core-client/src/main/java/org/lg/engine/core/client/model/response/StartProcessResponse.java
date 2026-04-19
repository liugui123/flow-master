
package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Getter
@Setter
@ToString
public class StartProcessResponse extends BaseWfResponse {

    private String procinstKey;

    private String startActinstKey;

    private String userTaskKey;
}
