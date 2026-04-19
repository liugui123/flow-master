package org.lg.engine.core.service.manager;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.lg.engine.core.client.enumerate.UserTaskFlagsEnum;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.*;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.common.constant.ModerJsonConstants;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuActinst;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.utils.Logs;

import java.util.*;
import java.util.stream.Collectors;


public class ProcConfHelper {

    /**
     * 获取流程定义中的节点列表
     *
     * @param procJson 流程定义数据
     * @return 节点列表
     */
    public static JsonNode getChildShapeJsonNodes(String procJson, String key) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(procJson);
        } catch (Exception e) {
            Logs.error("parse Json error", e);
            throw new WfException(e);
        }
        assert jsonNode != null;
        return jsonNode.get(key);
    }


    public static ChildShapes checkAndGetChildShapes(String procJson) {
        ChildShapes childShapes = JSON.parseObject(procJson, ChildShapes.class);
        Assert.isTrue(
                childShapes != null,
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getCode()
        );
        return childShapes;
    }

    /**
     * 获取某个子节点信息
     *
     * @param childShapes 子节点列表
     * @param procKey     单个子节点key
     * @return 单个子节点信息
     */
    public static ChildShape getChildShape(JsonNode childShapes, String procKey) {
        for (JsonNode childShape : childShapes) {
            JsonNode jsonNode = childShape.get(ModerJsonConstants.EDITOR_SHAPE_ID);
            if (jsonNode == null || !jsonNode.textValue().equals(procKey)) {
                continue;
            }
            return JSON.parseObject(childShape.toString(), ChildShape.class);
        }
        return null;
    }

    /**
     * 获取流程配置中某个子节点信息
     *
     * @param actKey   子节点key
     * @param procJson 流程配置
     * @return 单个子节点信息
     */
    public static ChildShape getChildShapeByActKeyAndProcJson(String actKey, String procJson) {
        JsonNode childShapes = ProcConfHelper.getChildShapeJsonNodes(procJson, ModerJsonConstants.EDITOR_CHILD_SHAPES);
        return ProcConfHelper.getChildShape(childShapes, actKey);
    }


    public static ChildShape getChildShapeByActJson(String actJson) {
        if (Utils.isEmpty(actJson)) {
            return null;
        }
        return JSON.parseObject(actJson, ChildShape.class);
    }


    public static ChildShape checkAndGetChildShapeByActJson(String actJson) {
        Assert.isTrue(actJson != null,
                WfExceptionCode.ACT_CONF_BLANK.getMsg(),
                WfExceptionCode.ACT_CONF_BLANK.getCode()
        );
        ChildShape childShape = JSON.parseObject(actJson, ChildShape.class);
        Assert.isTrue(childShape != null,
                WfExceptionCode.ACT_CONF_BLANK.getMsg(),
                WfExceptionCode.ACT_CONF_BLANK.getCode()
        );
        return childShape;
    }


    public static int findUserTaskFlags(Long actId) {

        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actId);
        Assert.isTrue(ruActinst != null,
                WfExceptionCode.ACTINST_BLANK.getMsg(),
                WfExceptionCode.ACTINST_BLANK.getCode()
        );
        ChildShape childShapeByActJson = ProcConfHelper.getChildShapeByActJson(ruActinst.getVarJson());
        if (childShapeByActJson != null) {
            return childShapeByActJson.getMultipleAssignee();
        }
        return UserTaskFlagsEnum.NONE.getFlag();
    }

    /**
     * 节点上的候选人
     */
    private static Collection<Assignee> getNoneAssignees(String sourceKey, String varJson) {
        ChildShape childShape = ProcConfHelper.getChildShapeByActJson(varJson);
        if (childShape == null) {
            return new HashSet<>(0);
        }
        ChildShapeAssigneeInfo noneAssignee = childShape.getNoneAssignee();
        if (noneAssignee == null) {
            return new HashSet<>(0);
        }
        return getAssigneesByChildShapeAssigneeInfo(sourceKey, noneAssignee);
    }

    /**
     * 节点上的抄送人
     */
    private static Collection<Assignee> getInformers(String sourceKey, String varJson) {
        ChildShape childShape = ProcConfHelper.getChildShapeByActJson(varJson);
        if (childShape == null) {
            return new HashSet<>(0);
        }
        ChildShapeAssigneeInfo assigneeInfo = childShape.getInformerInfo();
        if (assigneeInfo == null) {
            return new HashSet<>(0);
        }
        return getAssigneesByChildShapeAssigneeInfo(sourceKey, assigneeInfo);
    }

    /**
     * 节点上的处理人
     */
    private static Collection<Assignee> getAssignees(String sourceKey, String varJson) {
        ChildShape childShape = ProcConfHelper.getChildShapeByActJson(varJson);
        if (childShape == null) {
            return new HashSet<>(0);
        }
        ChildShapeAssigneeInfo assigneeInfo = childShape.getAssigneeInfo();
        Collection<Assignee> assigneesByChildShapeAssigneeInfo = getAssigneesByChildShapeAssigneeInfo(
                sourceKey,
                assigneeInfo
        );
        if (Utils.isNotEmpty(assigneesByChildShapeAssigneeInfo)) {
            return assigneesByChildShapeAssigneeInfo;
        }
        return getAssigneesByChildShapeAssigneeInfo(sourceKey, childShape.getNoneAssignee());
    }


    /**
     * 获取节点上面的人员信息 节点上面配置的动态的用户参数 去拉三方的人员信息   处理人 发起人的 所在公司 所在部门 所在岗位等等参数拼接
     */
    public static Collection<Assignee> getAssigneesByChildShapeAssigneeInfo(String sourceKey,
                                                                            ChildShapeAssigneeInfo assigneeInfo) {
        return initAssignees(sourceKey, assigneeInfo);
    }

    /**
     * 节点上面配置的动态的用户参数 去拉三方的人员信息
     * <p>
     * 新增 多部门的配置信息处理规则 >> 需要三方接口支持多部门的扩展
     */
    public static Collection<Assignee> initAssignees(String sourceKey,
                                                     ChildShapeAssigneeInfo assigneeInfo) {

        //新增多部门的处理逻辑  根据 actInstId 查询当前节点是否是多部门节点

        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();

        Collection<Assignee> assignees = new HashSet<>(1024);
        if (assigneeInfo == null) {
            return assignees;
        }

        //写死的 处理人 人员信息 处理人写死的情况 就不管多部门了 发任务的时候 就确认了部门
        if (Utils.isNotEmpty(assigneeInfo.getAssignees())) {
            assignees.addAll(assigneeInfo.getAssignees());
        }

        // 是否来自发起人
        if (assigneeInfo.getFromStarter() != null && assigneeInfo.getFromStarter()) {
            // 流程启动人的用户信息 启动人 多部门 产品说可以先不管 >> 需要三方接口的兼容和配合
            Assignee starter = new Assignee();
            starter.setId(ruProcinst.getStarterId());
            starter.setName(ruProcinst.getStarterName());
            starter.setDeptId(ruProcinst.getStarterDeptId());
            starter.setDeptName(ruProcinst.getStarterDeptName());
            starter.setOrgId(ruProcinst.getStarterOrgId());
            starter.setOrgName(ruProcinst.getStarterOrgName());
            assignees.add(starter);
        }
        // 是否来自节点
        List<String> formNodes = assigneeInfo.getFromNodes();
        if (Utils.isNotEmpty(formNodes)) {
            Set<NodeAssignee> assigneesFromNodes = RunTimeManager.getAssigneesFromNodes(formNodes, ruProcinst.getId());
            assignees.addAll(assigneesFromNodes);
        }
        //外接服务 组装外部服务调用链接
        Service service = assigneeInfo.getService();
        if (service != null) {
            List<Assignee> assignee = ServiceModelHelper.getAssignee(service.getId(), service.getParams());
            if (CollectionUtils.isNotEmpty(assignee)) {
                assignees.addAll(assignee);
            }
        }
        // 这里最终会根据 范围选择的一些条件 查询出来一批用户新信息
        return distinctAssignees(assignees);
    }

    public static Set<Assignee> distinctAssignees(Collection<Assignee> assignees) {
        Set<Assignee> res = new HashSet<>(assignees.size());
        if (Utils.isNotEmpty(assignees)) {
            Logs.info("get user distinctAssignees list ：{}", JSON.toJSONString(assignees));
            List<Assignee> collect = assignees.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(item -> item.getId() + ";" + (item.getDeptId() == null ? "" : item.getDeptId()) + ";" + (item.getOrgId() == null ? "" : item.getOrgId())))), ArrayList::new));
            res.addAll(collect);
        }
        return res;
    }

    /**
     * 获取自动完成节点 获取下个节点的 节点上配置的人员信息规则
     *
     * @param actinstId
     * @return
     */
    public static Collection<Assignee> checkAndGetAutoAssignees(Long actinstId) {
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actinstId);
        Assert.isTrue(ruActinst != null,
                WfExceptionCode.ACTINST_BLANK.getMsg(),
                WfExceptionCode.ACTINST_BLANK.getCode()
        );
        // 节点上面配置的动态的用户参数 去拉三方的人员信息   处理人 发起人的 所在公司 所在部门 所在岗位等等参数拼接
        Collection<Assignee> assignees = ProcConfHelper.getAssignees(ruActinst.getActinstKey(), ruActinst.getVarJson());
        Logs.info("checkAndGetAutoAssignees ：{}", JSON.toJSONString(assignees));
        //初始化当前节点的抄送人和下个节点的处理人
        String varJson = CommandContextFactory.getCommandContext().getVarJson();
        // 配置的写死的 任务处理人
        Collection<Assignee> assigneesFromForm = ProcConfHelper.getAssigneesFromFormAndNodes(varJson, ruActinst);

        if (Utils.isNotEmpty(assigneesFromForm)) {
            assignees.addAll(assigneesFromForm);
        }
        ChildShape childShape = ProcConfHelper.checkAndGetChildShapeByActJson(ruActinst.getVarJson());
        Boolean filterSelf = childShape.getFilterSelf();
        if (filterSelf != null && filterSelf) {
            List<String> assigneeIds = CommandContextFactory.getCommandContext().getAssigneeIds();
            if (Utils.isNotEmpty(assigneeIds)) {
                Logs.info("处理人过滤自己人员：{}", assigneeIds);
                assignees = assignees.stream().filter(a -> !assigneeIds.contains(a.getId()))
                        .collect(Collectors.toSet());
            }
        }
        if (Utils.isEmpty(assignees)) {
            Assert.isTrue(Utils.isNotEmpty(assignees),
                    WfExceptionCode.ASSIGNEE_BLANK.getMsg(),
                    WfExceptionCode.ASSIGNEE_BLANK.getCode(),
                    "处理人员和候选人为空，节点实例ID:{}",
                    actinstId
            );
        }
        return assignees;
    }

    public static Map<String, String> varJsonToMap(String varJson) {
        Map<String, String> res = new HashMap<>();
        if (Utils.isEmpty(varJson)) {
            return res;
        }
        try {
            Map map = JSON.parseObject(varJson, Map.class);
            for (Object key : map.keySet()) {
                if (key == null) {
                    continue;
                }
                Object val = map.get(key);
                if (val == null) {
                    continue;
                }
                String sVal = val.toString();
                res.put(key.toString(), sVal.replace(" ", ""));
            }
            return res;
        } catch (Exception e) {
            Logs.error(WfConstant.WF_LOG_PREFIX + "转换流程变量异常,实例变量{},错误{}", varJson, e.getMessage());
            return res;
        }
    }


    public static Set<Assignee> getAssigneesFromFormAndNodes(String varJson, RuActinst ruActinst) {
        if (Utils.isEmpty(varJson)) {
            return new HashSet<>(0);
        }
        if (ruActinst == null) {
            return new HashSet<>(0);
        }
        ChildShape childShapeByActJson = getChildShapeByActJson(ruActinst.getVarJson());
        if (childShapeByActJson == null) {
            return new HashSet<>(0);
        }
        // 配置的写死的 任务处理人
        ChildShapeAssigneeInfo assigneeInfo = childShapeByActJson.getAssigneeInfo();
        Set<Assignee> res = new HashSet<>();
        if (assigneeInfo != null) {
            String formUserKey = assigneeInfo.getFormUserKey();
            if (Utils.isNotEmpty(formUserKey)) {
                //来自表单
                Map map = JSON.parseObject(varJson, Map.class);
                Object o = map.get(formUserKey);
                if (o != null) {
                    Collection<Assignee> assignees = JSON.parseArray(JSON.toJSONString(o), Assignee.class);
                    // 用户核心参数是否缺失 校验
                    checkFormUsers(assignees);
                    res.addAll(assignees);

                }
            }
            List<String> formNodes1 = assigneeInfo.getFromNodes();
            if (Utils.isNotEmpty(formNodes1)) {
                //来自节点
                Set<NodeAssignee> assignees = RunTimeManager
                        .getAssigneesFromNodes(formNodes1, ruActinst.getProcinstId());
                res.addAll(assignees);
            }
        }
        return res;
    }

    private static void checkFormUsers(Collection<Assignee> assignees) {
        try {
            if (Utils.isNotEmpty(assignees)) {
                for (Assignee assignee : assignees) {
                    if (assignee == null) {
                        continue;
                    }
                    Utils.validateEntity(assignee);
                }
            }
        } catch (Exception e) {
            Assert.isTrue(false,
                    WfExceptionCode.FORM_USER_ERROR.getMsg(),
                    WfExceptionCode.FORM_USER_ERROR.getCode(),
                    "表单人员获取错误：{}",
                    e.getMessage());
            throw e;
        }

    }

    public static void updateProcinstVar(Long procinstId, Map<String, String> procinstVarJsonDb) {
        CommandApplications.getProcinstService().updateVarJsonById(JSON.toJSONString(procinstVarJsonDb), procinstId);
    }

    /**
     * 筛选出开始节点的配置
     */
    public static ChildShape getStartChildShape(String procJson) {
        if (Utils.isEmpty(procJson)) {
            return new ChildShape();
        }
        ChildShapes childShapes = checkAndGetChildShapes(procJson);
        List<ChildShape> childShapesList = childShapes.getChildShapes();
        if (Utils.isEmpty(childShapesList)) {
            return new ChildShape();
        }
        List<String> outgoingIds = new ArrayList<>(1024);

        for (ChildShape childShape : childShapesList) {
            List<ChildShapeOutgoing> outgoing = childShape.getOutgoing();
            if (Utils.isEmpty(outgoing)) {
                continue;
            }
            outgoingIds.addAll(outgoing.stream().map(ChildShapeOutgoing::getResourceId).collect(Collectors.toList()));
        }
        //筛选出开始节点的配置
        for (ChildShape childShape : childShapesList) {
            if (childShape == null) {
                continue;
            }
            String resourceId = childShape.getResourceId();

            if (!outgoingIds.contains(resourceId)) {
                return childShape;
            }
        }
        return new ChildShape();
    }
}
