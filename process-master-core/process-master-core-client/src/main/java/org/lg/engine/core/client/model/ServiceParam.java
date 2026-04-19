package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * liugui
 * @Date 2021/11/23
 */
@Getter
@Setter
public class ServiceParam {
    /**
     * 字段名字
     */
    private String fieldName;

    /**
     * 字段标识
     */
    private String fieldKey;

    /**
     * 字段组件类型
     */
    private String fieldValue;
}
