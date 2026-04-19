package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 自动退回配置
 * liugui
 * @Date 2022/2/16
 */
@Getter
@Setter
public class BackConfig {

    /**
     * 退回节点
     */
    private List<String> nodes;
}
