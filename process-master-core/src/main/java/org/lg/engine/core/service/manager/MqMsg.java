package org.lg.engine.core.service.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lg.engine.core.client.model.Operator;

/**
 * 流程开始、结束、错误消息体
 */
@Getter
@Setter
@ToString
public class MqMsg {

    private String procinstKey;

    private String procFormKey;

    private String procFormDataKey;

    private Integer procinstFlags;

    private String userTaskId;

    private String bizId;

    private Operator operator;

    private Long occurTime;
}
