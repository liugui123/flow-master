package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 外部接口
 */
@Getter
@Setter
public class Service {

    /**
     *
     */
    private Long id;
    /**
     * 详情名字
     */
    private String name;
    /**
     * 参数
     */
    private List<ServiceParam> params;
}
