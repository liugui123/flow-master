package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class ProcessListResponse extends BaseWfResponse {


    private Long id;


    private String procName;


    private String procKey;


    private Integer status;


    private String procFormKey;

}
