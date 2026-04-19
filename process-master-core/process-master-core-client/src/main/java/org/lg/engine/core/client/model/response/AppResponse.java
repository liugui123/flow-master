package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

@Setter
@Getter
public class AppResponse extends BaseWfResponse {

    private String id;


    private String appName;


    private String appIcon;


    private String appDesc;


    private String appUrl;

}
