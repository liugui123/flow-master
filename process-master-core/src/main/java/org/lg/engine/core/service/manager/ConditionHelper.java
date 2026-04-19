package org.lg.engine.core.service.manager;

import org.lg.engine.core.client.enumerate.ConditionRelStatusEnum;
import org.lg.engine.core.client.enumerate.ConditionTypeEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.*;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.conf.SpringHolder;
import org.lg.engine.core.service.AbstractConditionService;
import org.lg.engine.core.utils.Logs;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lg.engine.core.client.exception.WfExceptionCode.ACT_CUSTOM_CONDITION_GROUP_EMPTY;

/**
 * @author liugui
 * @Date 2021/11/25
 */
@Component
public class ConditionHelper {

    @Resource(name = "apiConditionService")
    AbstractConditionService apiConditionService;

    @Resource(name = "formConditionService")
    AbstractConditionService formConditionService;


    /**
     * 判断节点条件是否可以通过
     */
    public static boolean conditionPass(ChildShapeCondition condition,
                                        Map<String, String> varJson,
                                        Assignee starter,
                                        Operator operator,
                                        String nodeKey) {
        //没有设置时默认true
        if (condition == null) {
            return true;
        }

        if (ConditionTypeEnum.DEFAULT.getType().equals(condition.getConditionType())) {
            return true;
        }
        if (Utils.isEmpty(varJson)) {
            Logs.error(WfConstant.WF_LOG_PREFIX + "流程实例[{}]的设置条件是自定义条件，但是流程变量为空", nodeKey);
            return false;
        }
        //条件组之间的关系
        String conditionRel = condition.getRel();

        List<ChildShapeConditionGroup> childShapeConditionGroups = new ArrayList<>(1024);

        childShapeConditionGroups.addAll(condition.getApiConditionGroups());
        childShapeConditionGroups.addAll(condition.getFormConditionGroups());

        // 自定义的逻辑规则 >>> 这里会去跑表达式 自定义的规则
        if (ConditionTypeEnum.CUSTOM.getType().equals(condition.getConditionType())) {
            return conditionGroupPass(childShapeConditionGroups, varJson, starter, operator, conditionRel);
        }
        return false;
    }

    // 开始批量的处理逻辑判断
    public boolean compare(
            Map<String, String> procinstVarJsonDb,
            List<ChildShapeCon> conditions,
            String rel,
            Assignee starter,
            Operator operator) {

        Assert.isTrue(Utils.isNotEmpty(conditions),
                WfExceptionCode.CUSTOM_CONDITION_BLANK.getMsg(),
                WfExceptionCode.CUSTOM_CONDITION_BLANK.getCode()
        );

        if (ConditionRelStatusEnum.AND.getStatus().equals(rel)) {
            // 开始批量的处理逻辑判断
            for (ChildShapeCon condition : conditions) {
                if (condition == null) {
                    return false;
                }
                if (condition instanceof ChildShapeFormCon) {
                    boolean pass = formConditionService.pass(procinstVarJsonDb, condition, starter, operator);
                    if (!pass) {
                        return pass;
                    }
                }
                if (condition instanceof ChildShapeApiCon) {
                    boolean pass = apiConditionService.pass(procinstVarJsonDb, condition, starter, operator);
                    if (!pass) {
                        return pass;
                    }
                }
            }
            return true;
        } else {
            // 开始批量的处理逻辑判断
            for (ChildShapeCon condition : conditions) {
                if (condition == null) {
                    return false;
                }

                if (condition instanceof ChildShapeFormCon) {
                    boolean pass = formConditionService.pass(procinstVarJsonDb, condition, starter, operator);
                    if (pass) {
                        return pass;
                    }
                }
                if (condition instanceof ChildShapeApiCon) {
                    boolean pass = apiConditionService.pass(procinstVarJsonDb, condition, starter, operator);
                    if (pass) {
                        return pass;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 条件组判断结果输出
     */
    public static boolean conditionGroupPass(List<ChildShapeConditionGroup> childShapeConditionGroups,
                                             Map<String, String> varJson,
                                             Assignee starter,
                                             Operator operator,
                                             String conditionGroupRel) {

        //条件组为空时,抛出异常
        Assert.isTrue(Utils.isNotEmpty(childShapeConditionGroups),
                ACT_CUSTOM_CONDITION_GROUP_EMPTY.getMsg(),
                ACT_CUSTOM_CONDITION_GROUP_EMPTY.getCode()
        );

        if (ConditionRelStatusEnum.AND.getStatus().equals(conditionGroupRel)) {
            //所有的条件都必须满足
            for (ChildShapeConditionGroup conditionGroup : childShapeConditionGroups) {
                if (conditionGroup == null) {
                    return false;
                }

                //rel_条件关系 and 条件都必须满足 or 任意条件满足
                String rel = conditionGroup.getRel();
                if (rel == null) {
                    continue;
                }
                //填充条件
                List<ChildShapeCon> conditions = getConditionInGroup(conditionGroup);
                //条件为空则跳过
                if (Utils.isEmpty(conditions)) {
                    continue;
                }

                ConditionHelper conditionManager = SpringHolder.getBean(ConditionHelper.class);

                boolean pass = conditionManager.compare(varJson, conditions, rel, starter, operator);
                if (!pass) {
                    return false;
                }
            }
            return true;
        } else {
            //有一个满足就行
            for (ChildShapeConditionGroup conditionGroup : childShapeConditionGroups) {
                if (conditionGroup == null) {
                    return false;
                }

                //rel_条件关系 and 条件都必须满足 or 任意条件满足
                String rel = conditionGroup.getRel();
                if (rel == null) {
                    continue;
                }

                //填充条件
                List<ChildShapeCon> conditions = getConditionInGroup(conditionGroup);
                //条件为空则跳过
                if (Utils.isEmpty(conditions)) {
                    continue;
                }
                ConditionHelper conditonManager = SpringHolder.getBean(ConditionHelper.class);

                boolean pass = conditonManager.compare(varJson, conditions, rel, starter, operator);
                if (pass) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 获取条件组里的所有条件
     */
    private static List<ChildShapeCon> getConditionInGroup(ChildShapeConditionGroup conditionGroup) {
        List<ChildShapeCon> conditions = new ArrayList<>(1024);

        if (conditionGroup == null) {
            return conditions;
        }

        //判断表单条件组结果
        if (conditionGroup instanceof ChildShapeFormConditionGroup) {
            ChildShapeFormConditionGroup formConditionGroup = (ChildShapeFormConditionGroup) conditionGroup;
            conditions.addAll(formConditionGroup.getConditions());
        }
        //判断表单条件组结果
        if (conditionGroup instanceof ChildShapeApiConditionGroup) {
            ChildShapeApiConditionGroup apiConditionGroup = (ChildShapeApiConditionGroup) conditionGroup;
            conditions.addAll(apiConditionGroup.getConditions());
        }

        return conditions;
    }

}
