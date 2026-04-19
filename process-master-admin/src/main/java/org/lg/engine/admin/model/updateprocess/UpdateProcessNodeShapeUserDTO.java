package org.lg.engine.admin.model.updateprocess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProcessNodeShapeUserDTO {

    private java.util.Set<String> assigneesStr;
    private Boolean fromStarter;
    private String formUserKey;
    private java.util.List<String> fromNodes;
    private org.lg.engine.core.client.model.Service services;
    private java.util.List<org.lg.engine.core.client.model.AssigneeCate> assigneeSources;
    private org.lg.engine.core.client.model.ChildShapeSysTask thirdServer;

}
