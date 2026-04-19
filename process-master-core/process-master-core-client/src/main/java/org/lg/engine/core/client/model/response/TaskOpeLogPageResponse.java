
package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Getter
@Setter
@ToString
public class TaskOpeLogPageResponse extends BaseWfResponse {

    
    private String procinstName;

    
    private String procinstKey;

    
    private Long gmtCreate;

    
    private String taskDesc;

    
    private String operatorName;
}
