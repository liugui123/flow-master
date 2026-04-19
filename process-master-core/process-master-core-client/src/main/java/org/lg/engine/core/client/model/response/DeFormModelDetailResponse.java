package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class DeFormModelDetailResponse extends BaseWfResponse {


    private Long id;


    private Long formModelId;


    private String fieldName;


    private String fieldKey;


    private String fieldCptType;
}
