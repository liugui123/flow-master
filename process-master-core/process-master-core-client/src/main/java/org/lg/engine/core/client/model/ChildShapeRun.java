
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildShapeRun {

    private String resourceId;

    //状态 0运行中 1已完成 2 待处理
    private Integer status;
}
