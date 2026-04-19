package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Getter
@Setter
@ToString
public class ProcessUpdateResponse extends BaseWfResponse {


    private Long id;


    protected String procKey;


    protected String procName;


    protected String procJson;


    protected String procViewJson;


    protected Integer procVersion;



    private String procFormKey;


}
