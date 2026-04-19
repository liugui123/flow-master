
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 三方服务配置
 */
@Getter
@Setter
public class ChildShapeSysTaskServer {
    /**
     * 服务id
     */
    private Long serverId;
    /**
     * 服务名称
     */
    private String name;
    /**
     * 服务参数
     */
    private List<ChildShapeSysTaskServerParam> params;

}
