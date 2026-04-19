package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
public class ReFormModelDetailByKeyResponse extends BaseWfResponse {


    private Long id;


    private String formModelName;

}
