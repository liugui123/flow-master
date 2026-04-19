package org.lg.engine.core.service.manager;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;

/**
 * liugui
 *
 * @Date 2022/4/7
 */
@Getter
@Setter
public class BackMqMsg {

    private String procinstKey;

    private String procFormKey;

    private String procFormDataKey;

    private Integer procinstFlags;

    private String bizId;

    private Operator operator;

    private Long occurTime;
}
