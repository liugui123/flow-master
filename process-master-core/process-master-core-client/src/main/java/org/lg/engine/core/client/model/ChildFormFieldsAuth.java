package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单字段权限
 */
@Getter
@Setter
public class ChildFormFieldsAuth {
    /**
     * 字段key
     */
    private String key;
    /**
     * read
     * write
     * hide
     */
    private String auth;
}
