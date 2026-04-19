
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChildShapeUser {

    private String resourceId;

    private ChildShapeProperties properties;

    private String actType;

    private String viewConf;

    private Integer multipleAssignee = 4;

    private Integer conditionType = 0;

    private String rel;

}
