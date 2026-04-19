package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.enumerate.BeforSignBackTypeEnum;

/**
 * liugui
 * @Date 2022/4/14
 */
@Getter
@Setter
public class SignConfig {

    /**
     * @see BeforSignBackTypeEnum
     */
    private String beforeSignBackType;

}
