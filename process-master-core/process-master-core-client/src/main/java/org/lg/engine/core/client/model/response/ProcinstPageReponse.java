
package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

import java.util.List;

@Getter
@Setter
@ToString
public class ProcinstPageReponse extends BaseWfResponse {

    private Long id;

    private Long gmtCreate;


    private String procName;


    private String starterName;


    private String procKey;


    private Integer flags;


    List<ProcinstActinstResponse> actinsts;

    List<String> assignees;

}
