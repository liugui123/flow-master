package org.lg.engine.core.client.model.response.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础类
 */
@Setter
@Getter
public class BaseWfResponse implements Serializable{
    /**
     * 应用标识
     */
    private String appKey;
}
