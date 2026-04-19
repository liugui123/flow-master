package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程按钮权限
 */
@Getter
@Setter
public class ChildProcessBtnAuth {
    /**
     * 字段key
     */
    private String key;
    /**
     * read
     * hide
     */
    private String auth;
}
