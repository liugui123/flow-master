package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.enumerate.ActInstUrgeTypeEnum;
import org.lg.engine.core.client.enumerate.ActinstUrgeLevelEnum;

/**
 * 节点催办提醒配置
 * liugui
 * @Date 2022/2/15
 */

@Getter
@Setter
public class ActInstUrgeConfig {

    /**
     * 提醒方式
     * @see ActInstUrgeTypeEnum
     */
    private String type;
    /**
     * 缓急程度
     * @see ActinstUrgeLevelEnum
     */
    private Integer level;
    /**
     * 提醒时间配置
     */
    private TimeConfig timeConfig;
    /**
     * 自动提醒配置
     */
    private RemindAssigneeConfig remindConfig;
    /**
     * 自动退回配置
     */
    private BackConfig backConfig;
    /**
     * 自动转交配置
     */
    private DeliverConfig deliverConfig;

}
