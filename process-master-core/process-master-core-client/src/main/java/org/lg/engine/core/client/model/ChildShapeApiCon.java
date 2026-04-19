
package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接口条件
 */
@Getter
@Setter
public class ChildShapeApiCon extends ChildShapeCon{

    /**
     * 接口条件
     */
//    private ApiCondition apiCondition;

    private String id;

    private List<ServiceParam> params;
    /**
     * 表达式, ${data.id}=1这种
     */
//    private String expression;

}
