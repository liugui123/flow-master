package org.lg.engine.core.service;

import org.lg.engine.core.client.enumerate.ChildShapeConTypeEnum;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.ChildShapeCon;
import org.lg.engine.core.client.model.Operator;

import java.util.Map;

/**
 * @author liugui
 * @Date 2021/11/25
 */
public abstract class AbstractConditionService {


    public abstract boolean pass(Map<String, String> procinstVarJsonDb, ChildShapeCon condition, Assignee starter,
                                 Operator operator);


    public abstract ChildShapeConTypeEnum support();
}
