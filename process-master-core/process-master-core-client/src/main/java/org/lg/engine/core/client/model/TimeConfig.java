package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 自动提醒时间配置
 * liugui
 * @Date 2022/2/15
 */
@Getter
@Setter
public class TimeConfig {

    /**
     * 上午开始时间24h
     */
    private String amStartHour;

    /**
     * 上午开始时间56m
     */
    private String amStartMin;

    /**
     * 下午开始时间24h
     */
    private String pmStartHour;

    /**
     * 下午开始时间56m
     */
    private String pmStartMin;

    /**
     * 延迟 单位s
     *
     */
    private String pushDelay;
    /**
     * 推送间隔 单位s
     */
    private Integer pushInterval;



}
