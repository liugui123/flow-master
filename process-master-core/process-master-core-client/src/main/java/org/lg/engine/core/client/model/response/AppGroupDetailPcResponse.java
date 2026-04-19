package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

import java.util.List;

@Setter
@Getter
public class AppGroupDetailPcResponse extends BaseWfResponse {

    private Long pid;

    private Long id;

    private String appName;

    /**
     * Key
     */
    private String appDetailKey;

    private String detailKey;

    private String detailName;

    private String procFormKey;

    private String parentAppDetailKey;

    private Integer flags;

    private String extra;

    private Integer status;

    private List<AppGroupDetailPcResponse> children;

    private Integer sort;

}
