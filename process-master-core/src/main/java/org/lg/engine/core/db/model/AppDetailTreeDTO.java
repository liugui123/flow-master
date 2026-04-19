package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class AppDetailTreeDTO {

    private Long id;

    private String appName;

    /**
     * Key
     */
    private String appDetailKey;

    private String detailKey;

    private String detailName;

    private String procFormKey;

    private Long pid;

    private String parentAppDetailKey;

    private Integer flags;

    private String extra;

    private Integer status;

    private List<AppDetailTreeDTO> children;

    private Integer sort;

    private String iconUrl;
}
