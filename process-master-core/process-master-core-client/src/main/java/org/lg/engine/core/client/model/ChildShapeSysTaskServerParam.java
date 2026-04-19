
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 三方服务参数配置
 */
@Getter
@Setter
public class ChildShapeSysTaskServerParam {

    /**
     * 第三方参数字段
     */
    private String param;
    /**
     * 第三方参数名称
     */
    private String paramName;
    /**
     * 对应值
     */
    private String value;
    /**
     * 对应值名称
     */
    private String valueName;
    /**
     * 参数类型 1-来自表单， 2-输入
     */
    private Integer type;

}
