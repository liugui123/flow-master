package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;


@Getter
@Setter
@ToString
public class ProcinstActinstResponse extends BaseWfResponse {

    private Long id;


    private String actinstName;


    private String actinstKey;
}