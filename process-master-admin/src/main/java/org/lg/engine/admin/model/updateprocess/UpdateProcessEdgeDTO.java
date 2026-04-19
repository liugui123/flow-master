package org.lg.engine.admin.model.updateprocess;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.ChildShape;

@Getter
@Setter
public class UpdateProcessEdgeDTO extends ChildShape {

    /**
     * 源头id
     */
    protected String sourceId;

    /**
     * 目标id
     */
    private String targetId;


}
