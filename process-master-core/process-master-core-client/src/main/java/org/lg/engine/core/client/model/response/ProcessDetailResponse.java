package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class ProcessDetailResponse extends BaseWfResponse {

    private Long id;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer status;


    private Integer flags;


    private String procViewJson;


    private Integer procVersion;


    private String procKey;


    private String procName;


    private String procConfJson;

    private Long formModelReId;
}
