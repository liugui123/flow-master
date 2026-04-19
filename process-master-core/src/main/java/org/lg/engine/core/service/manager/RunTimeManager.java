package org.lg.engine.core.service.manager;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.*;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.*;
import org.lg.engine.core.client.model.request.CompleteTaskRequest;
import org.lg.engine.core.client.model.request.ManualCompleteInfo;
import org.lg.engine.core.client.model.request.ManualStartInfo;
import org.lg.engine.core.client.model.response.CompleteTaskResponse;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.common.constant.ConditionConstants;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContext;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.listener.event.WfEngineEventType;
import org.lg.engine.core.listener.event.WfEventBuilder;
import org.lg.engine.core.utils.Logs;
import org.lg.engine.core.utils.WfUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class RunTimeManager {

    // userAfterSignDone 后加签 走的这个自动完成的方法 userAfterSignDone
    public static CompleteTaskResponse autoFinishTaskAndLeave(boolean afterSignDone,
                                                              RuTaskUser ruTaskUser,
                                                              String optionDesc,
                                                              String comment,
                                                              String procinstVarJson,
                                                              Collection<Assignee> assignees) {
        CompleteTaskResponse response = new CompleteTaskResponse();
        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        if (!afterSignDone) {
            delUserTaskAndSendCopyTask(ruTaskUser, comment, UserTaskStatusEnum.COMMON_FINISH);
        }
        //分发任务
        boolean readTask = (UserTaskFlagsEnum.READ_TASK.getFlag() & ruTaskUser.getFlags()) != 0;
        //抄送任务
        boolean copyTask = (UserTaskFlagsEnum.COPY.getFlag() & ruTaskUser.getFlags()) != 0;
        //允许通过
        boolean enablePass =
                enableFinishSubTaskAndDelRemainSubUserTask(ruTaskUser) &&
                        enableFinishTaskAndDelRemainUserTask(ruTask.getId(), ruTaskUser.getFlags());
        Logs.info("[RunTimeManager.autoFinishTaskAndLeave] ruTaskUser: {}, assigneeInfo: {}, readTask: {}, copyTask: {}, enablePass: {}", ruTaskUser, assignees, readTask, copyTask, enablePass);
        if (enablePass || readTask || copyTask) {
            if (enablePass) {
                // 删除任务
                delTask(ruTask, WfConstant.DEL_TASK);
                // t_wf_ru_actinst 任务修改为 已完成状态
                signActStatusFinished(ruTask.getActinstId());
                // 发消息通知其他对接的渠道
                CommandApplications.getEventDispatcher()
                        .dispatchEvent(
                                WfEventBuilder.createEvent(
                                        WfEngineEventType.AUTO_ACT_COMPLETED,
                                        ruTask.getProcinstId(),
                                        ruTask.getId(),
                                        ruTask.getActinstId()
                                )
                        );
                // 查询 t_wf_hi_ru_task_user 任务
                List<String> assigneeIdByTaskId = CommandApplications.getHiRuTaskUserService()
                        .selectAssigneeIdByTaskId(ruTaskUser.getTaskId());
                if (CollectionUtils.isEmpty(assigneeIdByTaskId)) {
                    assigneeIdByTaskId = new ArrayList<>();
                }
                // 过滤上个节点的已办 和 未办 => 或签的情况
                Set<String> assigneeIds = CommandApplications.getTaskUserService().selectAssigneeIdByTaskId(ruTaskUser.getTaskId());
                if (CollectionUtils.isNotEmpty(assigneeIds)) {
                    assigneeIdByTaskId.addAll(assigneeIds);
                }
                assigneeIdByTaskId.add(CommandContextFactory.getCommandContext().getOperator().getId());
                CommandContextFactory.getCommandContext().setAssigneeIds(assigneeIdByTaskId);

                // 动态执行任务的相关的脚本
//                List<RuSysTask> afterSysTasks = CommandApplications.getSysTaskService()
//                        .listRuSysTasksByActinstIdAndExecLocationAndStatus(ruTask.getActinstId(),
//                                SysTaskExecLocationEnum.ACTINST_AFTER.getId(), SysTaskStatusEnum.INIT.getId());
//                if (CollectionUtils.isNotEmpty(afterSysTasks)) {
//                    Collections.sort(afterSysTasks, Comparator.comparing(RuSysTask::getSort));
//                    Assignee starter = getStarter(CommandContextFactory.getCommandContext().getRuProcinst());
//                    Map<String, Object> proinstvarMap = getProcinstVar(starter,
//                            CommandContextFactory.getCommandContext().getOperator(), ruTask.getProcinstId(),
//                            procinstVarJson);
//                    boolean success = CommandApplications.getSysTaskService().doSysTasks(afterSysTasks, proinstvarMap);
//                    if (!success) {
//                        //写入写入临时数据
//                        RuActinstTempData actinstTempData = new RuActinstTempData();
//                        long timestamp = System.currentTimeMillis();
//                        actinstTempData.setGmtCreate(timestamp);
//                        actinstTempData.setGmtModified(timestamp);
//                        actinstTempData.setActinstId(ruTask.getActinstId());
//                        actinstTempData.setProcinstId(ruTask.getProcinstId());
//                        actinstTempData.setDataType(SysTaskExecLocationEnum.ACTINST_AFTER.getId());
//                        RuActinstSysTaskAutoData autoData = new RuActinstSysTaskAutoData();
//                        autoData.setActinstId(ruTask.getActinstId());
//                        autoData.setProcinstId(ruTask.getProcinstId());
//                        autoData.setAssigneeInfo(assigneeInfo);
//                        autoData.setProcinstVarJson(procinstVarJson);
//                        RuActinstAfterSysTaskTempData sysTaskTempData = new RuActinstAfterSysTaskTempData();
//                        sysTaskTempData.setAutoData(autoData);
//                        sysTaskTempData.setCommandContext(CommandContextFactory.getCommandContext());
//                        sysTaskTempData.setUserTaskMode(ActInstTaskModeEnum.AUTO.getId());
//                        actinstTempData.setTempData(JSON.toJSONString(sysTaskTempData));
//                        CommandApplications.getRuActinstTempDataService().insert(actinstTempData);
//                        return afterFinishTaskAndLeave(ruTaskUser, procinstVarJson, response);
//                    }
//                }
            }
            // 修改任务信息 -- 判断 下个节点能不能过 >> 创建下个节点和任务
            // 1.查出下面节点的线 然后根据线上面的逻辑判断 这个线能不能过
            // 2.线的逻辑通过 去创建节点信息和用户任务
            autoLeaveAct(ruTask.getProcinstId(),
                    ruTask.getActinstId(),
                    procinstVarJson,
                    assignees);

        }
        response.setEnd(CommandContextFactory.getCommandContext().getEnd());
        return afterFinishTaskAndLeave(ruTaskUser, procinstVarJson, response);
    }


    /**
     * // 个签 前后加签
     *
     * @param ruTaskUser
     * @param procinstVarJson
     * @param response
     * @return
     */
    private static CompleteTaskResponse afterFinishTaskAndLeave(RuTaskUser ruTaskUser, String procinstVarJson,
                                                                CompleteTaskResponse response) {
        Long userTaskIdPid = ruTaskUser.getPid();
        if (userTaskIdPid != null) {
            if (UserTaskFlagsEnum.INDIVIDUAL_SIGN.hasTag(ruTaskUser.getFlags())) {
                activeIndividualSignTaskUser(userTaskIdPid);
            } else if (UserTaskFlagsEnum.BEFORE_SIGN.hasTag(ruTaskUser.getFlags())) {
                if (!checkHasRunSubTasksWithRecursion(ruTaskUser.getTaskId(), userTaskIdPid)) {
                    activeBeforeSignTaskUser(userTaskIdPid);
                }
            } else if (UserTaskFlagsEnum.AFTER_SIGN.hasTag(ruTaskUser.getFlags())) {
                List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByPid(userTaskIdPid);
                if (Utils.isEmpty(ruTaskUsers)) {
                    RuTaskUser parentUserTask = CommandApplications.getTaskUserService()
                            .checkAndGetUserTaskFromAll(userTaskIdPid);
                    CompleteTaskResponse response1 = autoFinishTaskAndLeave(true,
                            parentUserTask,
                            "后加签自动完成",
                            null,
                            procinstVarJson,
                            null);
                    return response1;
                }
            }

        }
        return response;
    }

    /**
     * 判断是否有子任务
     */
    private static boolean checkHasRunSubTasksWithRecursion(Long taskId, Long pid) {
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByTaskId(taskId);
        SetMultimap<Long, Long> pidAndIdRelMap = HashMultimap.create();
        for (RuTaskUser ruTaskUser : ruTaskUsers) {
            pidAndIdRelMap.put(ruTaskUser.getPid(), ruTaskUser.getId());
        }
        SetMultimap<Long, Long> hiPidAndIdRelMap = HashMultimap.create();
        List<HiRuTaskUser> hiRuTaskUsers = CommandApplications.getHiRuTaskUserService()
                .selectByTaskIdIn(Lists.newArrayList(taskId));
        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
            hiPidAndIdRelMap.put(hiRuTaskUser.getPid(), hiRuTaskUser.getUserTaskId());
        }
        Set<Long> levelPidSet = new HashSet<>();
        levelPidSet.add(pid);
        Set<Long> subIdSet = findSubTasks(Sets.newHashSet(pid), pidAndIdRelMap, hiPidAndIdRelMap);
        while (CollectionUtils.isNotEmpty(subIdSet)) {
            levelPidSet.addAll(subIdSet);
            subIdSet = findSubTasks(subIdSet, pidAndIdRelMap, hiPidAndIdRelMap);
        }
        for (Long id : levelPidSet) {
            if (pidAndIdRelMap.containsKey(id)) {
                return true;
            }
        }
        return false;

    }


    private static Set<Long> findSubTasks(Set<Long> pidSet, SetMultimap<Long, Long> pidAndIdRelMap,
                                          SetMultimap<Long, Long> hiPidAndIdRelMap) {
        Set<Long> result = new HashSet<>();
        if (CollectionUtils.isEmpty(pidSet)) {
            return result;
        }
        for (Long pid : pidSet) {
            Set<Long> idSet = pidAndIdRelMap.get(pid);
            if (idSet != null) {
                result.addAll(idSet);
            }
            Set<Long> hiPidSet = hiPidAndIdRelMap.get(pid);
            if (hiPidSet != null) {
                result.addAll(hiPidSet);
            }
        }
        return result;
    }

    /**
     * 手动完成用户任务 >> 选节点进入 >>
     */
    public static CompleteTaskResponse manualFinishTaskAndLeave(RuTaskUser ruTaskUser,
                                                                String optionDesc,
                                                                String comment,
                                                                List<ManualCompleteInfo> manualCompleteInfos,
                                                                String varJson) {
        Logs.info("[RunTimeManager.manualFinishTaskAndLeave] ruTaskUser: {}, manualCompleteInfos: {}", ruTaskUser, manualCompleteInfos);
        Assert.isTrue(
                Utils.isNotEmpty(manualCompleteInfos),
                WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getMsg(),
                WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getCode()
        );
        CompleteTaskResponse response = new CompleteTaskResponse();
        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        // 正常通过(提交按钮) + 待办中心的任务批量完成
        delUserTaskAndSendCopyTask(ruTaskUser, comment, UserTaskStatusEnum.COMMON_FINISH);
        boolean readTask = (UserTaskFlagsEnum.READ_TASK.getFlag() & ruTaskUser.getFlags()) != 0;
        boolean copyTask = (UserTaskFlagsEnum.COPY.getFlag() & ruTaskUser.getFlags()) != 0;
        boolean enablePass = enableFinishSubTaskAndDelRemainSubUserTask(ruTaskUser) &&
                enableFinishTaskAndDelRemainUserTask(ruTask.getId(), ruTaskUser.getFlags());
        Logs.info("[RunTimeManager.manualFinishTaskAndLeave] readTask: {}, copyTask: {}, enablePass: {}", readTask, copyTask, enablePass);

        // 完成当前的用户信息 >> 转移ru记录到hi 发mq消息 改状态 节点的脚本 api接口 等等操作
        if (copyTask || readTask || enablePass) {
            if (enablePass) {
                // 删除 run 的任务 hi_run 新增一条任务
                delTask(ruTask, WfConstant.DEL_TASK);
                // 任务修改为 已完成状态
                signActStatusFinished(ruTask.getActinstId());
                // mq消息
                CommandApplications.getEventDispatcher()
                        .dispatchEvent(
                                WfEventBuilder.createEvent(
                                        WfEngineEventType.MANUAL_ACT_COMPLETED,
                                        ruTask.getProcinstId(),
                                        ruTask.getId(),
                                        ruTask.getActinstId()
                                )
                        );
                // 获取初始化状态的运行中的 任务
//                List<RuSysTask> afterSysTasks = CommandApplications.getSysTaskService()
//                        .listRuSysTasksByActinstIdAndExecLocationAndStatus(ruTask.getActinstId(),
//                                SysTaskExecLocationEnum.ACTINST_AFTER.getId(), SysTaskStatusEnum.INIT.getId());
//                if (CollectionUtils.isNotEmpty(afterSysTasks)) {
//                    Collections.sort(afterSysTasks, Comparator.comparing(RuSysTask::getSort));
//                    Assignee starter = getStarter(CommandContextFactory.getCommandContext().getRuProcinst());
//                    Map<String, Object> proinstvarMap = getProcinstVar(
//                            starter,
//                            CommandContextFactory.getCommandContext().getOperator(),
//                            ruTask.getProcinstId(),
//                            varJson);
//                    // 执行任务 脚本 api接口 等等
//                    boolean success = CommandApplications.getSysTaskService().doSysTasks(afterSysTasks, proinstvarMap);
//                    if (!success) {
//                        //写入写入临时数据
//                        RuActinstTempData actinstTempData = new RuActinstTempData();
//                        long timestamp = System.currentTimeMillis();
//                        actinstTempData.setGmtCreate(timestamp);
//                        actinstTempData.setGmtModified(timestamp);
//                        actinstTempData.setActinstId(ruTask.getActinstId());
//                        actinstTempData.setProcinstId(ruTask.getProcinstId());
//                        actinstTempData.setDataType(SysTaskExecLocationEnum.ACTINST_AFTER.getId());
//                        RuActinstSysTaskManualData manualData = new RuActinstSysTaskManualData();
//                        manualData.setActinstId(ruTask.getActinstId());
//                        manualData.setProcinstId(ruTask.getProcinstId());
//                        manualData.setManualCompleteInfos(manualCompleteInfos);
//                        RuActinstAfterSysTaskTempData sysTaskTempData = new RuActinstAfterSysTaskTempData();
//                        sysTaskTempData.setManualData(manualData);
//                        sysTaskTempData.setCommandContext(CommandContextFactory.getCommandContext());
//                        sysTaskTempData.setUserTaskMode(ActInstTaskModeEnum.MANUAL.getId());
//                        actinstTempData.setTempData(JSON.toJSONString(sysTaskTempData));
//                        // 流程节点临时数据表 t_wf_ru_actinst_temp_data
//                        CommandApplications.getRuActinstTempDataService().insert(actinstTempData);
//                        // 个签 前加签 后加签
//                        return afterFinishTaskAndLeave(ruTaskUser, null, response);
//                    }
//                }
            }
            // 手动提交节点完成任务  传入的多个节点信息处理逻辑
            manualLeaveAct(ruTask.getProcinstId(),
                    ruTask.getActinstId(),
                    manualCompleteInfos);

        }

        response.setEnd(CommandContextFactory.getCommandContext().getEnd());
        // 个签 前后加签
        return afterFinishTaskAndLeave(ruTaskUser, null, response);
    }

    /**
     * 直送 处理传入的直送节点
     */
    public static CompleteTaskResponse directFinishTaskAndLeave(RuTaskUser ruTaskUser,
                                                                String optionDesc,
                                                                String comment,
                                                                List<ManualCompleteInfo> manualCompleteInfos,
                                                                String varJson) {
        Logs.info("[RunTimeManager.directFinishTaskAndLeave] ruTaskUser: {}, manualCompleteInfos: {}", ruTaskUser, manualCompleteInfos);
        Assert.isTrue(
                Utils.isNotEmpty(manualCompleteInfos),
                WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getMsg(),
                WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getCode()
        );
        CompleteTaskResponse response = new CompleteTaskResponse();
        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        // 正常通过(提交按钮) + 待办中心的任务批量完成
        delUserTaskAndSendCopyTask(ruTaskUser, comment, UserTaskStatusEnum.COMMON_FINISH);
        boolean r = (8192 & ruTaskUser.getFlags()) != 0;
        boolean r2 = (1 & ruTaskUser.getFlags()) != 0;
        boolean enablepass = enableFinishSubTaskAndDelRemainSubUserTask(ruTaskUser) &&
                enableFinishTaskAndDelRemainUserTask(ruTask.getId(), ruTaskUser.getFlags());
        Logs.info("[RunTimeManager.manualFinishTaskAndLeave] r: {}, r2: {}, enablepass: {}", r, r2, enablepass);

        // 完成当前的用户信息 >> 转移ru记录到hi 发mq消息 改状态 节点的脚本 api接口 等等操作
        if (r2 || r || enablepass) {
            if (enablepass) {
                // 删除 run 的任务 hi_run 新增一条任务
                delTask(ruTask, WfConstant.DEL_TASK);
                // 任务修改为 已完成状态
                signActStatusFinished(ruTask.getActinstId());
                // mq消息
                CommandApplications.getEventDispatcher()
                        .dispatchEvent(
                                WfEventBuilder.createEvent(
                                        WfEngineEventType.MANUAL_ACT_COMPLETED,
                                        ruTask.getProcinstId(),
                                        ruTask.getId(),
                                        ruTask.getActinstId()
                                )
                        );
            }

        }

        response.setEnd(CommandContextFactory.getCommandContext().getEnd());
        // 个签 前后加签
        return afterFinishTaskAndLeave(ruTaskUser, null, response);
    }

    /**
     * 同级节点执行优先级
     * 开始 > 任务、分发、抄送、触发 > 包容网关 > 分发网关 > 结束
     */

    public static void autoLeaveAct(Long procinstId,
                                    Long currentFlowActinstId,
                                    String procinstVarJson,
                                    Collection<Assignee> assignees) {
        //连接线和目标节点id
        Map<Long, Long> sequenceIdAndNextFlowActIdRel =
                getSequenceIdAndNextFlowActIdRel(currentFlowActinstId);

        Assert.isTrue(Utils.isNotEmpty(sequenceIdAndNextFlowActIdRel),
                WfExceptionCode.NEXT_USER_ACT_BLANK.getMsg(),
                WfExceptionCode.NEXT_USER_ACT_BLANK.getCode());

        List<Long> sequenceTargetIds = new ArrayList<>(sequenceIdAndNextFlowActIdRel.keySet());
        //线和线的条件类型
        Map<Long, Integer> sequenceIdAndTypeRel = getSequenceIdAndTypeRel(sequenceTargetIds);

        //线和线的优先级
        // 同级别节点执行优先级，按照开始 > 触发>任务>分发>抄送> 包容网关 > 分发网关 > 结束分组
        List<SortedSequence> sortedSequences = getSortedSequence(sequenceIdAndNextFlowActIdRel);
        // 找出线的目标节点并执行下一级
        List<Long> elseSeq = new ArrayList<>();
        int notPassTotal = 0;
        for (SortedSequence sortedSequence : sortedSequences) {
            Long sequenceTargetId = sortedSequence.getId();
            if (ConditionTypeEnum.ELSE.getType().equals(sequenceIdAndTypeRel.get(sequenceTargetId))) {
                elseSeq.add(sequenceTargetId);
                notPassTotal++;
                if (notPassTotal == sequenceTargetIds.size()) {
                    // 根据当前的类型去执行 节点 线
                    boolean end = runActinstAndSignEnd(
                            procinstId,
                            elseSeq.get(0),
                            assignees
                    );
                    if (end) {
                        CommandContextFactory.getCommandContext().setEnd(true);
                    }
                    return;
                }
                continue;
            }
            // 根据线上面的配置的条件 去判断这根线 能不能往下走
            boolean pass = runSequenceFlowAndReturnPassRes(
                    procinstId,
                    procinstVarJson,
                    sequenceTargetId
            );
            if (!pass) {
                notPassTotal++;
                if (notPassTotal == sequenceTargetIds.size()) {
                    //没有else条件直接弹出
                    if (Utils.isEmpty(elseSeq)) {
                        Assert.isTrue(false,
                                WfExceptionCode.NEXT_USER_ACT_CAN_NOT_FOUND.getMsg(),
                                WfExceptionCode.NEXT_USER_ACT_CAN_NOT_FOUND.getCode());
                    } else {
                        // 根据当前的类型去执行 节点 线 用户任务
                        boolean end = runActinstAndSignEnd(
                                procinstId,
                                elseSeq.get(0),
                                assignees
                        );
                        if (end) {
                            CommandContextFactory.getCommandContext().setEnd(true);
                        }
                        return;
                    }
                }
                continue;
            }
            // 当前节点的下面一个节点ID
            Long nextActinstId = sequenceIdAndNextFlowActIdRel.get(sequenceTargetId);
            boolean end = runActinstAndSignEnd(
                    procinstId,
                    nextActinstId,
                    assignees
            );
            if (end) {
                CommandContextFactory.getCommandContext().setEnd(true);
                return;
            }
        }

        signActStatusFinished(currentFlowActinstId);
    }

    /**
     * 给线排序
     * 同级别节点执行优先级，按照开始 > 触发>任务>分发>抄送> 包容网关 > 分发网关 > 结束分组
     */
    private static List<SortedSequence> getSortedSequence(Map<Long, Long> sequenceIdAndNextFlowActIdRel) {
        List<SortedSequence> res = new ArrayList<>(64);
        if (Utils.isEmpty(sequenceIdAndNextFlowActIdRel)) {
            return res;
        }
        //连接线
        List<Long> sequenceIds = new ArrayList<>(sequenceIdAndNextFlowActIdRel.keySet());
        if (Utils.isEmpty(sequenceIds)) {
            return res;
        }
        //目标节点
        Collection<Long> targetIds = sequenceIdAndNextFlowActIdRel.values();
        if (Utils.isEmpty(targetIds)) {
            return res;
        }
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectActinstTypeByIdIn(targetIds);
        if (Utils.isEmpty(ruActinsts)) {
            return res;
        }
        Map<Long, String> actIdAndType = ruActinsts.stream().collect(Collectors.toMap(RuActinst::getId, RuActinst::getActinstType));

        if (Utils.isEmpty(sequenceIds)) {
            return new ArrayList<>(0);
        }
        for (Long sequenceId : sequenceIds) {
            if (sequenceId == null) {
                continue;
            }
            Long targetId = sequenceIdAndNextFlowActIdRel.get(sequenceId);
            if (targetId == null) {
                continue;
            }
            SortedSequence sortedSequence = new SortedSequence();
            sortedSequence.setId(sequenceId);
            String type = actIdAndType.get(targetId);
            sortedSequence.setSort(ActInstTypeEnum.getSortByType(type));
            res.add(sortedSequence);
        }
        res.sort(Comparator.comparing(SortedSequence::getSort));
        return res;
    }

    public static Map<Long, Integer> getSequenceIdAndTypeRel(Collection<Long> sequenceTargetIds) {
        Map<Long, Integer> res = new HashMap<>();
        // t_wf_ru_actinst 节点信息
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectByIdIn(sequenceTargetIds);
        if (Utils.isEmpty(ruActinsts)) {
            return res;
        }
        for (RuActinst ruActinst : ruActinsts) {
            if (ruActinst == null) {
                continue;
            }
            String varJson = ruActinst.getVarJson();
            ChildShape childShapeByActJson = ProcConfHelper.getChildShapeByActJson(varJson);
            if (childShapeByActJson == null) {
                continue;
            }
            ChildShapeCondition condition = childShapeByActJson.getCondition();
            if (condition == null) {
                //没有设置条件时，默认空
                continue;
            }
            //conditionType_条件类型 自定义 1 默认 0 ?
            res.put(ruActinst.getId(), condition.getConditionType());
        }
        return res;
    }

    /**
     * 手动提交节点完成任务  传入的多个节点信息处理逻辑
     */
    public static void manualLeaveAct(Long procinstId,
                                      Long currentFlowActinstId,
                                      List<ManualCompleteInfo> manualCompleteInfos) {
        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        Long startActinstId = ruTask.getActinstId();

        Set<String> actKeys = manualCompleteInfos.stream()
                .map(ManualCompleteInfo::getActKey)
                .collect(Collectors.toSet());
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectByActinstKeyInAndProcinstId(actKeys, procinstId);
        //查询actinst的key和id映射
        Map<String, Long> keyAndIdMap = ruActinsts.stream()
                .collect(Collectors.toMap(RuActinst::getActinstKey, RuActinst::getId));
        //特殊逻辑处理，分发汇聚节点要放到最后（因为分发汇聚节点判断是否结束等待是以是否有运行中的任务来判断）
        if (CollectionUtils.isNotEmpty(manualCompleteInfos) && manualCompleteInfos.size() > 1) {
//            Set<Long> actinstIdSet = manualCompleteInfos.stream().map(v -> v.getActinstId())
//                    .collect(Collectors.toSet());
            // 获取节点的类型 和id  >> 用户节点，系统节点，线和id
            Map<String, String> keyAndActinstTypeMap = ruActinsts.stream()
                    .collect(Collectors.toMap(RuActinst::getActinstKey, RuActinst::getActinstType));
            manualCompleteInfos.forEach(v -> {
                String actinstType = keyAndActinstTypeMap.get(v.getActKey());
                if (ActInstTypeEnum.READ_GATEWAY.getType().equals(actinstType)) {
                    v.setSort(Integer.MAX_VALUE - 1);
                } else if (ActInstTypeEnum.END_EVENT.getType().equals(actinstType)) {
                    // 当结束节点和其他节点 碰撞到一起去了 如果结束节点一但 先执行 就会导致其他流程全部被丢弃
                    // 结束节点 必须最后一个执行
                    v.setSort(Integer.MAX_VALUE);
                } else {
                    v.setSort(0);
                }
            });
            // 升序
            manualCompleteInfos.sort(Comparator.comparing(ManualCompleteInfo::getSort));
        }
        // 开始处理接口的入参信息 修改任务
        for (ManualCompleteInfo manualCompleteInfo : manualCompleteInfos) {
            if (manualCompleteInfo == null) {
                continue;
            }
            // t_wf_ru_actinst_site
            Set<Long> sequenceTargetIds = RunTimeManager.selectTatgetIdsByActinstId(startActinstId);
            Long actinstId = keyAndIdMap.get(manualCompleteInfo.getActKey());
            // t_wf_ru_actinst_site
            Set<Long> sourceActinstIds = RunTimeManager.selectsActinstIdsByByTatgetId(actinstId);
            sequenceTargetIds.retainAll(sourceActinstIds);
            List<Long> resActinstIds = new ArrayList<>(sequenceTargetIds);
            Assert.isTrue(resActinstIds.size() == 1,
                    WfExceptionCode.MANUAL_SOURCE_SEC_FLOW_ILLEGAL.getMsg(),
                    WfExceptionCode.MANUAL_SOURCE_SEC_FLOW_ILLEGAL.getCode()
            );
            // 运行时节点实例 修改状态为已完成 ??????
            signActStatusFinished(resActinstIds.get(0));
//            ChildShapeAssigneeInfo assigneeInfo = manualCompleteInfo.getAssigneeInfo();
//            if (assigneeInfo == null) {
//                assigneeInfo = new ChildShapeAssigneeInfo();
//            }
            //查询 当前流程实例的节点类型 用户节点 还是线 系统节点
            // 这里会取出来很多节点 然后循环去处理 不同节点的逻辑 => 前加签 后加签 等等一些节点逻辑  manualCompleteInfo.getActinstId()
            boolean end = runActinstAndSignEnd(
                    procinstId,
                    actinstId,
                    manualCompleteInfo.getAssignees()
            );
            if (end) {
                // 已知 情况1.当前循环中存在结束节点，那么就会进入到这里 >>结束整个流程 情况2.
                CommandContextFactory.getCommandContext().setEnd(true);
                return;
            }
        }
        // 节点完成 t_wf_ru_actinst 已完成
        signActStatusFinished(currentFlowActinstId);
    }


    /**
     * 判断是否能够通过并行网关
     */
    private static boolean checkCanPassInclusiveGateway(Long procinstId, Long actinstId) {
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectByProcInstId(procinstId);
        Map<Long, RuActinst> idAndActinstRel = ruActinsts.stream()
                .collect(Collectors.toMap(RuActinst::getId, Function.identity(), (x, y) -> y));
        List<RuActinstSite> ruActinstSites = CommandApplications.getActinstSiteService().selectByProInstId(procinstId);
        List<Long> parallelActinstIds = new ArrayList<>();
        findParallelActinstIdsUp(actinstId, parallelActinstIds, ruActinstSites, idAndActinstRel);
        if (CollectionUtils.isEmpty(parallelActinstIds)) {
            return true;
        }
        // 已完成的节点数量
        List<Long> finishActinstList = CommandApplications.getActinstService().selectIdByIdInAndStatus(parallelActinstIds, ActinstStatusEnum.FINISHED.getCode());

        // 修改逻辑 汇聚节点以上方已激活的节点为主 如果有处理中的节点 就不算全部通过 所有激活的节点全部完成 就算全部通过
        List<Long> finishActinstListPending = CommandApplications.getActinstService().selectIdByIdInAndStatus(parallelActinstIds, ActinstStatusEnum.RUNNING.getCode());

        return !CollectionUtils.isEmpty(finishActinstList) && CollectionUtils.isEmpty(finishActinstListPending);

//        Set<Long> taskIdSet = CommandApplications.getRuTaskService()
//                .selectTaskIdByActinstIdsFromAll(parallelActinstIds);
//        return CommandApplications.getTaskUserService()
//                .countByTaskIdInAndStatusNot(taskIdSet, UserTaskStatusEnum.FINISHED.getStatus()) == 0;

    }


    public static void findParallelActinstIdsUp(Long actinstId, List<Long> parallelActinstIds,
                                                List<RuActinstSite> ruActinstSites, Map<Long, RuActinst> idAndActinstRel) {
        RuActinst actinst = idAndActinstRel.get(actinstId);
        if (actinst == null || ActInstTypeEnum.START_EVENT.getType().equals(actinst.getActinstType())
                || ActInstTypeEnum.COPY.getType().equals(actinst.getActinstType())) {
            return;
        }
        Set<Long> sourceActinstIds = findSourceActinstIds(actinstId, ruActinstSites);
        parallelActinstIds.addAll(sourceActinstIds);
    }


    public static boolean runActinstAndSignEnd(
            Long procinstId,
            Long actinstId,
            Collection<Assignee> assignees) {
        //查询 当前任务实例的节点类型 用户节点 还是线 系统节点
        String type = CommandApplications.getActinstService().selectActinstTypeById(actinstId);
        if (ActInstTypeEnum.END_EVENT.getType().equals(type)) {
//            boolean success = judgeAndDoSysTasksBeforeActinst(procinstId, actinstId, assigneeInfo, procinstVarJson,
//                    type);
//            if (!success) {
//                Logs.error("judgeAndDoSysTasksBeforeActinst error, procinstId:{}, actinstId:{}, type:{}", procinstId,
//                        actinstId, type);
//                return false;
//            }
            doEndNoneEventActinst(procinstId, actinstId);
            return true;
        } else if (ActInstTypeEnum.SEQUENCE_FLOW.getType().equals(type)) {
            runSequenceFlowAndReturnPassRes(procinstId, null, actinstId);
            Long nextFlowActId = getNextActFlowIdBySequenceFlowId(actinstId);
            return runActinstAndSignEnd(procinstId, nextFlowActId, assignees);
        } else if (ActInstTypeEnum.INCLUSIVE_GATEWAY.getType().equals(type)) {
            //进入到网关操作逻辑
            RuActinst ruActinst = RunTimeManager.checkAndGetRuActinst(actinstId);
            ChildShape childShape = ProcConfHelper.checkAndGetChildShapeByActJson(ruActinst.getVarJson());
            Integer passModel = childShape.getPassMode();
            if (ActPassModelEnum.ALL.getMode().equals(passModel)) {
                if (RunTimeManager.checkCanPassInclusiveGateway(procinstId, actinstId)) {
                    autoLeaveAct(procinstId, actinstId, null, null);
                }
            } else if (ActPassModelEnum.ONE.getMode().equals(passModel)) {
                autoLeaveAct(procinstId, actinstId, null, null);
            }
        } else if (ActInstTypeEnum.USER_TASK.getType().equals(type)) {
            // 创建下个节点的任务 >>>  这里去找下个节点 和下个节点的人员信息 自动+手动
            runUserTaskActinst(actinstId, assignees);
        } else if (ActInstTypeEnum.READ.getType().equals(type)) {
            runReadActinst(actinstId, assignees);
        } else if (ActInstTypeEnum.READ_GATEWAY.getType().equals(type)) {
            if (RunTimeManager.selectNotFinishReadTaskByAct(actinstId, procinstId, SearchModelEnum.UP) == 0) {
                autoLeaveAct(procinstId, actinstId, null, null);
            }
        } else if (ActInstTypeEnum.COPY.getType().equals(type)) {
            runCopyActinst(actinstId, assignees);
        } else if (ActInstTypeEnum.SERVICE.getType().equals(type)) {
            runServiceActinst(actinstId);
            autoLeaveAct(procinstId, actinstId, null, null);
        } else {
            runUserTaskActinst(actinstId, assignees);
        }
        return false;
    }

    private static void runCopyActinst(Long actinstId, Collection<Assignee> assignees) {
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actinstId);
        ChildShape childShapeByActJson = ProcConfHelper.checkAndGetChildShapeByActJson(ruActinst.getVarJson());
        String nodeMode = childShapeByActJson.getNodeMode();
        //节点处理人信息
//        Collection<Assignee> assignees;
        // 抄送节点上面配置的 手动或者自动的模式 抄送节点的特殊配置项
//        if (assigneeInfo != null && !NodeModelEnum.AUTO.getCode().equals(nodeMode)) {
//            assignees = ProcConfHelper.checkAndGetManualAssignees(assigneeInfo);
//        } else {
//            assignees = ProcConfHelper.checkAndGetAutoAssignees(actinstId);
//        }
        if (Utils.isEmpty(assignees)) {
            assignees = ProcConfHelper.checkAndGetAutoAssignees(actinstId);
        }
        RuTask ruTask = new RuTask();
        ruTask.setGmtCreate(System.currentTimeMillis());
        ruTask.setGmtModified(System.currentTimeMillis());
        ruTask.setTaskName(ruActinst.getActinstName());
        ruTask.setTaskKey(ruActinst.getActinstKey());
        ruTask.setProcinstId(CommandContextFactory.getCommandContext().getRuProcinst().getId());
        ruTask.setActinstId(ruActinst.getId());
        ruTask.setVarJson(ruActinst.getVarJson());
        ruTask.setStatus(0);
        ruTask.setFlags(0);
        CommandApplications.getRuTaskService().insert(ruTask);
        createCopyUserTasks(ruTask, assignees, ruTask.getTaskName(), 0);
    }

    /**
     * 尝试调用接口，不做其他事情
     */
    private static void runServiceActinst(Long actinstId) {
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actinstId);
        ChildShape childShapeByActJson = ProcConfHelper.checkAndGetChildShapeByActJson(ruActinst.getVarJson());

        List<Service> services = childShapeByActJson.getServices();
        if (Utils.isEmpty(services)) {
            return;
        }
        for (Service service : services) {
            if (service == null) {
                continue;
            }
            ServiceModelHelper.callService(service.getId(), service.getParams());
        }
    }


    public static void doEndNoneEventActinst(Long procinstId, Long actinstId) {
        deleteProcInst(
                procinstId,
                ProcInstStatusEnum.FINISH,
                UserTaskStatusEnum.COMMON_FINISH
        );
        CommandContext tempCommandContext = Convert.INSTANCE
                .commandContextToCommandContext(CommandContextFactory.getCommandContext());
        CommandContextFactory.setCommandContext(tempCommandContext);
        RuActinst ruActinst = checkAndGetRuActinst(actinstId);
        CommandContextFactory.getCommandContext().setRuActinst(ruActinst);
        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        if (ruTask != null) {
            CommandApplications.getEventDispatcher()
                    .dispatchEvent(
                            WfEventBuilder.createEvent(
                                    WfEngineEventType.AUTO_ACT_COMPLETED,
                                    procinstId,
                                    ruTask.getId(),
                                    ruActinst.getId()
                            )
                    );
        }
    }

    /**
     * 根据节点上面的配置 去获取人员信息 然后去创建节点
     */
    public static void runUserTaskActinst(
            Long flowActId,
            Collection<Assignee> assignees) {
        if (Utils.isEmpty(assignees)) {
            // 获取并且组装手动完成的人员信息
//            assignees = ProcConfHelper.checkAndGetManualAssignees(assigneeInfo);
            assignees = ProcConfHelper.checkAndGetAutoAssignees(flowActId);
        }
        int userTaskFlags = ProcConfHelper.findUserTaskFlags(flowActId);
//        if (UserTaskFlagsEnum.NONE != flagsEnum) {
//            userTaskFlags = userTaskFlags + flagsEnum.getFlag();
//        }
        //todo 这里的逻辑有点奇怪 没有传入人员时，即自动找人，检查代理人
//        boolean checkAgent = Utils.isEmpty(assignees);
        //无论何时，都尝试查找代理人
        // 创建节点任务 和 节点下的用户任务
        RunTimeManager.createTaskAndUserTasksAndReturnFirstUserTask(
                flowActId,
                assignees,
                TaskOptionTypeEnum.CREATE_TASK.getDesc(),
                userTaskFlags,
                true,
                true);
    }

    public static void runReadActinst(
            Long flowActId,
            Collection<Assignee> assignees) {
//        Collection<Assignee> assignees;
        if (Utils.isEmpty(assignees)) {
//            assignees = ProcConfHelper.checkAndGetManualAssignees(assigneeInfo);
            assignees = ProcConfHelper.checkAndGetAutoAssignees(flowActId);
        }
        RunTimeManager.createTaskAndUserTasksAndReturnFirstUserTask(
                flowActId,
                assignees,
                TaskOptionTypeEnum.CREATE_TASK.getDesc(),
                UserTaskFlagsEnum.READ_TASK.getFlag(),
                true,
                true);
    }

    private static Map<String, Object> getProcinstVar(Assignee starter, Operator operator, Long procinstId,
                                                      String procinstVarJson) {
        Map<String, Object> procinstVarMap = new HashMap<>();
        if (procinstId == null) {
            return procinstVarMap;
        }
        RuProcinst ruProcinst = checkAndGetRuProcinst(procinstId, "getProcinstVar");
        if (StringUtils.isNotBlank(ruProcinst.getVarJson())) {
            Map<String, Object> valJsonMap = JSON.parseObject(ruProcinst.getVarJson(), Map.class);
            if (MapUtils.isNotEmpty(valJsonMap)) {
                procinstVarMap.putAll(valJsonMap);
            }
        }
        if (Utils.isNotEmpty(procinstVarJson)) {
            Map<String, Object> valJsonMap = JSON.parseObject(procinstVarJson, Map.class);
            if (MapUtils.isNotEmpty(valJsonMap)) {
                procinstVarMap.putAll(valJsonMap);
            }
        }
        //设置默认流程变量，发起人、处理人
        procinstVarMap.put(ConditionConstants.API_CONDITION_HANDLER, operator.getId());
        procinstVarMap.put(ConditionConstants.API_CONDITION_HANDLER_DEPT, operator.getDeptId());
        procinstVarMap.put(ConditionConstants.API_CONDITION_HANDLER_ORG, operator.getOrgId());
        procinstVarMap.put(ConditionConstants.API_CONDITION_STARTER, starter.getId());
        procinstVarMap.put(ConditionConstants.API_CONDITION_STARTER_DEPT, starter.getDeptId());
        procinstVarMap.put(ConditionConstants.API_CONDITION_STARTER_ORG, starter.getOrgId());
        return procinstVarMap;
    }

    /**
     * 判断这个分支的线上面的逻辑 这个分支能不能通过 >>> 获取下个节点信息
     */
    public static boolean pass(Long sequenceId,
                               Long procinstId,
                               String procinstVarJson,
                               Assignee starter,
                               Operator operator) {
        Map<String, String> procinstVarJsonDb = selectProcinstVarById(procinstId);

        if (Utils.isNotEmpty(procinstVarJson)) {
            procinstVarJsonDb.putAll(ProcConfHelper.varJsonToMap(procinstVarJson));
            if (procinstId != null) {
                ProcConfHelper.updateProcinstVar(procinstId, procinstVarJsonDb);
            }
        }
        //节点信息
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(sequenceId);
        String varJson = ruActinst.getVarJson();
        // 节点配置
        ChildShape childShapeByActJson = ProcConfHelper.getChildShapeByActJson(varJson);
        Assert.isTrue(childShapeByActJson != null,
                WfExceptionCode.ACT_CONF_BLANK.getMsg(),
                WfExceptionCode.ACT_CONF_BLANK.getCode()
        );

        return ConditionHelper.conditionPass(childShapeByActJson.getCondition(), procinstVarJsonDb, starter, operator, procinstId + "");

    }

    /**
     * 判断线是否可以通过
     */
    public static boolean sequencePass(String actKey,
                                       String procKey,
                                       String procinstVarJson,
                                       Assignee starter,
                                       Operator operator) {
        ChildShape childShapeByActJson = CommandApplications.getReProcessService().getActConfByActKey(procKey, actKey);
        Assert.isTrue(childShapeByActJson != null,
                WfExceptionCode.ACT_CONF_BLANK.getMsg(),
                WfExceptionCode.ACT_CONF_BLANK.getCode()
        );

        Map<String, String> varJsonToMap = ProcConfHelper.varJsonToMap(procinstVarJson);
        return ConditionHelper.conditionPass(childShapeByActJson.getCondition(), varJsonToMap, starter, operator, procKey);

//        if (ConditionTypeEnum.DEFAULT.getType().equals(childShapeByActJson.getConditionType())) {
//            return true;
//        }
//        if (Utils.isEmpty(procinstVarJson)) {
//            Logs.error(WfConstant.WF_LOG_PREFIX + "流程定义编码[{}]的设置条件是自定义条件，但是流程变量为空", procKey);
//            return false;
//        }
//        if (ConditionTypeEnum.CUSTOM.getType().equals(childShapeByActJson.getConditionType())) {
//            String rel = childShapeByActJson.getRel();
//            Map<String, String> varJsonToMap = ProcConfHelper.varJsonToMap(procinstVarJson);
//            if (StringUtils.isBlank(starter.getDeptId())) {
//                String deptStr = varJsonToMap.get("_dept");
//                if (StringUtils.isNotBlank(deptStr)) {
//                    String[] depts = deptStr.split(",");
//                    for (String dept : depts) {
//                        if (dept.contains("value")) {
//                            String[] array1 = dept.split("=");
//                            String s1 = array1[1];
//                            if (s1.indexOf("}") > -1) {
//                                s1 = s1.substring(0, s1.indexOf("}"));
//                            }
//                            starter.setDeptId(s1);
//                        }
//                    }
//                }
//            }
//            ConditionHelper conditonManager = SpringHolder.getBean(ConditionHelper.class);
//            return conditonManager.compare(varJsonToMap, childShapeByActJson.getConditions(), rel,
//                    starter, operator);
//        }
//        return false;
    }

//    private static boolean continueCompare(Map<String, String> procinstVarJsonDb, List<ChildShapeCon> conditions,
//                                           ChildShapeCon condition, String conditionVal, String mc) {
//        try {
//            String[] conditionVals = conditionVal.split(",");
//            if (Utils.isEmpty(conditionVals)) {
//                return false;
//            }
//            List<ChildShapeCon> newConditions = new ArrayList<>(conditions.size());
//            for (String comparisonVal : conditionVals) {
//                ChildShapeCon childShapeCon = new ChildShapeCon();
//                childShapeCon.setComparisonVal(comparisonVal);
//                childShapeCon.setMc("eq");
//                childShapeCon.setSubjectKey(condition.getSubjectKey());
//                newConditions.add(childShapeCon);
//            }
//            return compare(procinstVarJsonDb, newConditions, mc, , );
//        } catch (Exception e) {
//            Logs.ERROR_LOG.error(WfConstant.WF_LOG_PREFIX + "转换表达式异常，自定义条件入参：{}，错误信息：{}", conditionVal, e.getMessage());
//            return false;
//        }
//    }


    /**
     * 获取流程实力外部变量，并转换成map
     */
    private static Map<String, String> selectProcinstVarById(Long procinstId) {
        if (procinstId == null) {
            return new HashMap<>();
        }
        RuProcinst ruProcinst = checkAndGetRuProcinst(procinstId, "selectProcinstVarById");
        return ProcConfHelper.varJsonToMap(ruProcinst.getVarJson());
    }


    public static boolean enableFinishTaskAndDelRemainUserTask(Long taskId, Integer userTaskFlags) {
        if (UserTaskFlagsEnum.OR_SIGN.hasTag(userTaskFlags)) {
            delUserTaskByTaskId(taskId, UserTaskStatusEnum.AUTO_COMPLETE);
            return true;
        }
        if ((UserTaskFlagsEnum.COPY.getFlag() & userTaskFlags) != 0) {
            Long countByTaskIdAndStatus = CommandApplications.
                    getHiRuTaskUserService().countByTaskIdAndStatus(taskId, 0);
            if (countByTaskIdAndStatus > 0) {
                return false;
            }
        }
        Long runTaskCount = CommandApplications.getTaskUserService().countIdByTaskId(taskId);
        return runTaskCount <= 0;
    }


    public static boolean enableFinishSubTaskAndDelRemainSubUserTask(RuTaskUser ruTaskUser) {
        Long userTaskIdPid = ruTaskUser.getPid();
        if (userTaskIdPid == null) {
            return true;
        }

        if (UserTaskFlagsEnum.INDIVIDUAL_SIGN.hasTag(ruTaskUser.getFlags())) {
            return false;
        } else if (UserTaskFlagsEnum.BEFORE_SIGN.hasTag(ruTaskUser.getFlags())) {
            return false;
        } else if (UserTaskFlagsEnum.AFTER_SIGN.hasTag(ruTaskUser.getFlags())) {
            return false;
        }
        if (UserTaskFlagsEnum.OR_SIGN.hasTag(ruTaskUser.getFlags())) {
            delUserTaskByPid(userTaskIdPid, UserTaskStatusEnum.AUTO_COMPLETE);
            return true;
        }
        Long runTaskCount = CommandApplications.getTaskUserService().countByPid(userTaskIdPid);
        return runTaskCount <= 0;
    }


    public static Map<Long, Long> getSequenceIdAndNextFlowActIdRel(Long currentFlowActinstId) {
        Map<Long, Long> sequenceIdAndNextFlowActIdRel = new HashMap<>();
        Set<Long> sequenceTargetIds = RunTimeManager.selectTatgetIdsByActinstId(currentFlowActinstId);
        for (Long sequenceTargetId : sequenceTargetIds) {
            Long nextFlowActId = getNextActFlowIdBySequenceFlowId(sequenceTargetId);
            sequenceIdAndNextFlowActIdRel.put(sequenceTargetId, nextFlowActId);
        }
        return sequenceIdAndNextFlowActIdRel;
    }


    public static Long getNextActFlowIdBySequenceFlowId(Long sequenceFlowId) {
        Set<Long> nextFlowActIds = RunTimeManager.selectTatgetIdsByActinstId(sequenceFlowId);
        if (nextFlowActIds == null || nextFlowActIds.size() != 1) {
            throw new WfException("连接线目标节点数量错误，sequenceId:" + sequenceFlowId);
        }
        return new ArrayList<Long>(nextFlowActIds).get(0);
    }


    /**
     * 创建节点任务 和 节点下的用户任务
     */
    public static RuTaskUser createTaskAndUserTasksAndReturnFirstUserTask(Long actinstId,
                                                                          Collection<Assignee> assignees,
                                                                          String desc,
                                                                          int userTaskFlags,
                                                                          Boolean checkAgent,
                                                                          boolean sync) {
        Logs.info("[RunTimeManager.createTaskAndUserTasksAndReturnFirstUserTask]. actinstId: {}, assignees: {}, userTaskFlags: {}, checkAgent: {}, sync: {}", actinstId, assignees, userTaskFlags, checkAgent, sync);
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actinstId);
        Assert.isTrue(!ActInstTypeEnum.SEQUENCE_FLOW.getType().equals(ruActinst.getActinstType()), "流程连接线不能生成任务");
        RuTask nextRuTask = new RuTask();
        nextRuTask.setGmtCreate(System.currentTimeMillis());
        nextRuTask.setGmtModified(System.currentTimeMillis());
        nextRuTask.setTaskName(ruActinst.getActinstName());
        nextRuTask.setTaskKey(ruActinst.getActinstKey());
        nextRuTask.setVarJson(ruActinst.getVarJson());
        nextRuTask.setProcinstId(CommandContextFactory.getCommandContext().getRuProcinst().getId());
        nextRuTask.setActinstId(ruActinst.getId());
        nextRuTask.setStatus(RuTaskStatusEnum.PENDING.getStatus());
        nextRuTask.setFlags(userTaskFlags);
        // 创建下个节点任务
        CommandApplications.getRuTaskService().insert(nextRuTask);
        CommandContextFactory.getCommandContext().setRuTask(nextRuTask);
        // 记录log
        saveRuTaskOperateLog(nextRuTask.getId(), desc);
        // 创建节点的用户任务 创建用户任务rutaskuser
        //创建用户任务rutaskuser
        RuTaskUser userTaskAndReturnFirst = createUserTaskAndReturnFirst(
                assignees,
                desc,
                nextRuTask.getId(),
                userTaskFlags,
                checkAgent,
                sync);
        signActStatusRunning(actinstId);
        //处理节点限时处理任务
//        handleActinstUrgeTasks(ruActinst.getProcinstId(), actinstId);
        return userTaskAndReturnFirst;
    }


    public static void saveRuTaskOperateLog(Long taskId, String taskDesc) {
        Operator operator = CommandContextFactory.getCommandContext().getOperator();
        RuTaskOperateLog ruTaskOperateLog = new RuTaskOperateLog();
        ruTaskOperateLog.setGmtCreate(System.currentTimeMillis());
        ruTaskOperateLog.setOperatorId(operator.getId());
        ruTaskOperateLog.setOperatorName(operator.getName());
        ruTaskOperateLog.setOperatorDeptId(operator.getDeptId());
        ruTaskOperateLog.setOperatorDeptName(operator.getDeptName());
        ruTaskOperateLog.setOperatorOrgId(operator.getOrgId());
        ruTaskOperateLog.setOperatorOrgName(operator.getOrgName());
        ruTaskOperateLog.setTaskId(taskId);
        ruTaskOperateLog.setFlags(0);
        ruTaskOperateLog.setStatus(0);
        ruTaskOperateLog.setTaskDesc(taskDesc);
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        if (ruProcinst != null) {
            ruTaskOperateLog.setProcinstKey(ruProcinst.getProcKey());
            ruTaskOperateLog.setProcinstName(ruProcinst.getProcName());
        }
        CommandApplications.getTaskOperateLogService().insert(ruTaskOperateLog);
    }


    public static RuTaskUser createUserTaskAndReturnFirst(Collection<Assignee> assignees,
                                                          String desc,
                                                          Long taskId,
                                                          int userTaskFlags,
                                                          Boolean checkAgent,
                                                          boolean sync) {
        if (Utils.isEmpty(assignees)) {
            return null;
        }
        List<RuTaskUser> ruTaskUsers =
                getRnUserTasksByAssigneesAndSaveAgent(assignees, desc, taskId, userTaskFlags);
        if (Utils.isEmpty(ruTaskUsers)) {
            return null;
        }
        checkAgentAndSaveUserTasks(userTaskFlags, checkAgent, ruTaskUsers, sync);
        return ruTaskUsers.get(0);
    }

    public static void checkAgentAndSaveUserTasks(int userTaskFlags,
                                                  Boolean checkAgent,
                                                  List<RuTaskUser> ruTaskUsers,
                                                  boolean sync) {
        if (Utils.isEmpty(ruTaskUsers)) {
            return;
        }
        String taskName = CommandContextFactory.getCommandContext().getRuTask().getTaskName();
        saveRuTaskUsers(userTaskFlags, ruTaskUsers, taskName, sync);
    }


    private static void saveRuTaskUsers(int userTaskFlags,
                                        List<RuTaskUser> ruTaskUsers,
                                        String userTaskTitle,
                                        boolean sync) {
        if (Utils.isEmpty(ruTaskUsers)) {
            return;
        }
        //处理任务标题 {发起人}的{流程名称}


        CommandApplications.getTaskUserService().batchInsert(ruTaskUsers);
        if (UserTaskFlagsEnum.INDIVIDUAL_SIGN.hasTag(userTaskFlags)) {
            for (int i = 1; i < ruTaskUsers.size(); i++) {
                RuTaskUser preTaskUser = ruTaskUsers.get(i - 1);
                ruTaskUsers.get(i).setPid(preTaskUser.getId());
            }
            List<RuTaskUser> ruTaskUserList = new ArrayList<>(1);
            ruTaskUserList.add(ruTaskUsers.get(ruTaskUsers.size() - 1));
            CommandApplications.getTaskUserService().updateBatch(ruTaskUsers);
        }
    }

    /**
     * 通过人员生成任务，并保存代理信息
     */
    public static List<RuTaskUser> getRnUserTasksByAssigneesAndSaveAgent(Collection<Assignee> assignees,
                                                                         String desc,
                                                                         Long taskId,
                                                                         int flags) {
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        //任务信息
        List<RuTaskUser> ruTaskUsers = new ArrayList<>(assignees.size());
        //代理人信息
        for (Assignee assignee : assignees) {
            RuTaskUser taskUser = new RuTaskUser();
            taskUser.setTaskId(taskId);
            taskUser.setUserTaskKey(UUID.randomUUID().toString().replace("-", ""));
            taskUser.setAssigneeId(assignee.getId());
            taskUser.setAssigneeName(assignee.getName());
            taskUser.setAssigneeDeptId(assignee.getDeptId());
            taskUser.setAssigneeDeptName(assignee.getDeptName());
            taskUser.setAssigneeOrgId(assignee.getOrgId());
            taskUser.setAssigneeOrgName(assignee.getOrgName());
            taskUser.setAssigneeMobile(assignee.getMobile());

            taskUser.setGmtCreate(System.currentTimeMillis());
            taskUser.setGmtModified(System.currentTimeMillis());
            taskUser.setStatus(UserTaskStatusEnum.PENDING.getStatus());
            taskUser.setFlags(flags);

            taskUser.setProcinstId(ruProcinst.getId());
            taskUser.setTaskDesc(desc);
            taskUser.setProcFormDataKey(ruProcinst.getProcFormDataKey());
            taskUser.setProcFormKey(ruProcinst.getProcFormKey());
            taskUser.setProcFormUrl(ruProcinst.getProcFormUrl());
            taskUser.setTaskType(ruProcinst.getTaskType());
            taskUser.setTaskTitle(ruProcinst.getTaskTitle());
            taskUser.setProcFormUrlApp(ruProcinst.getProcFormUrlApp());
            taskUser.setBizId(ruProcinst.getBizId());
            taskUser.setAppKey(ruProcinst.getAppKey());
            taskUser.setUserTaskLevel(CommandContextFactory.getCommandContext().getUserTaskLevel());
            ruTaskUsers.add(taskUser);

        }
        return ruTaskUsers;
    }


    public static Set<Long> selectTatgetIdsByActinstId(Long actinstId) {
        return CommandApplications.getActinstSiteService().selectTargetIdByActinstId(actinstId);
    }

    public static Set<Long> selectsActinstIdsByByTatgetId(Long tatgetActinstId) {
        return CommandApplications.getActinstSiteService().selectActinstIdByTargetId(tatgetActinstId);
    }

    public static List<Long> selectsFinishedActinstIdsByByTatgetId(Long tatgetActinstId) {
        Set<Long> sourceIds = CommandApplications.getActinstSiteService()
                .selectActinstIdByTargetId(tatgetActinstId);

        return CommandApplications.getActinstService().selectIdByIdInAndStatus(sourceIds,
                ActinstStatusEnum.FINISHED.getCode());
    }


    public static void signActStatusPending(Long actinstId) {
        CommandApplications.getActinstService().updateStatusById(ActinstStatusEnum.PENDING.getCode(), actinstId);
    }

    public static void signActStatusPending(List<Long> actinstIds) {
        CommandApplications.getActinstService().updateStatusByIdIn(ActinstStatusEnum.PENDING.getCode(), actinstIds);
    }


    public static void signActStatusRunning(Long actinstId) {
        CommandApplications.getActinstService().updateStatusById(ActinstStatusEnum.RUNNING.getCode(), actinstId);
    }


    /**
     * t_wf_ru_actinst 任务修改为 已完成状态
     *
     * @param actinstId
     */
    public static void signActStatusFinished(Long actinstId) {
        CommandApplications.getActinstService().updateStatusById(ActinstStatusEnum.FINISHED.getCode(), actinstId);
    }

    public static void signActStatusFinished(List<Long> actinstIds) {
        CommandApplications.getActinstService().updateStatusByIdIn(ActinstStatusEnum.FINISHED.getCode(), actinstIds);
    }


    public static void deleteProcInst(Long procinstId,
                                      ProcInstStatusEnum procInstStatusEnum,
                                      UserTaskStatusEnum statusEnum
    ) {
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        HiRuProcinst hiRuProcinst = Convert.INSTANCE.ruProcinstToHiRuProcinst(ruProcinst);
        hiRuProcinst.setProcinstId(procinstId);
        hiRuProcinst.setStatus(procInstStatusEnum.getStatus());
        CommandApplications.getHiRuProcinstService().insert(hiRuProcinst);
        CommandApplications.getProcinstService().deleteByPrimaryKey(procinstId);
        deleteTaskByProcInstId(procinstId, procInstStatusEnum.getDesc(), statusEnum);
        signActStatusFinishedByPrcInstId(procinstId);
        ruProcinst.setStatus(procInstStatusEnum.getStatus());
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        if (ProcInstStatusEnum.FINISH.equals(procInstStatusEnum)) {
            CommandApplications.getEventDispatcher().dispatchEvent(
                    WfEventBuilder.createEvent(
                            WfEngineEventType.PROCINST_COMPLETED,
                            procinstId,
                            null,
                            null
                    )
            );
        } else if (ProcInstStatusEnum.ABORTED.equals(procInstStatusEnum)) {
            CommandApplications.getEventDispatcher().dispatchEvent(
                    WfEventBuilder.createEvent(
                            WfEngineEventType.PROCINST_ABORTED,
                            procinstId,
                            null,
                            null
                    )
            );
        } else if (ProcInstStatusEnum.REFUSE.equals(procInstStatusEnum)) {
            CommandApplications.getEventDispatcher()
                    .dispatchEvent(
                            WfEventBuilder.createEvent(
                                    WfEngineEventType.PROCINST_REFUSE,
                                    ruProcinst.getId(),
                                    null,
                                    null
                            )
                    );
        } else if (ProcInstStatusEnum.REVOKE.equals(procInstStatusEnum)) {
            CommandApplications.getEventDispatcher()
                    .dispatchEvent(
                            WfEventBuilder.createEvent(
                                    WfEngineEventType.ACT_REVOKE,
                                    ruProcinst.getId(),
                                    null,
                                    null
                            )
                    );
        } else if (ProcInstStatusEnum.CANCEL.equals(procInstStatusEnum)) {
            CommandApplications.getEventDispatcher()
                    .dispatchEvent(
                            WfEventBuilder.createEvent(
                                    WfEngineEventType.ACT_CANCEL,
                                    ruProcinst.getId(),
                                    null,
                                    null
                            )
                    );
        }
    }


    public static void delTask(RuTask ruTask, String taskDesc) {
        // 删除 ru
        CommandApplications.getRuTaskService().deleteByPrimaryKey(ruTask.getId());
        // ru >> hi
        HiRuTask hiRuTask = Convert.INSTANCE.ruTaskToHiRuTask(ruTask);
        hiRuTask.setTaskId(ruTask.getId());
        // insert hi
        CommandApplications.getHiRuTaskService().insert(hiRuTask);
        // add log
        saveRuTaskOperateLog(ruTask.getId(), taskDesc);
    }


    public static RuTask checkAndGetRuTask(Long taskId) {
        return CommandApplications.getRuTaskService().checkAndGetRuTask(taskId);
    }

    public static RuTask checkAndGetRuTaskFromAll(Long taskId) {
        return CommandApplications.getRuTaskService().checkAndGetRuTaskFromAll(taskId);
    }


    public static RuTaskUser checkAndGetRuTaskUser(Long taskUserId) {
        RuTaskUser ruTaskUser = CommandApplications.getTaskUserService().selectByPrimaryKey(taskUserId);
        Assert.isTrue(ruTaskUser != null,
                WfExceptionCode.USER_TASK_BLANK.getMsg(),
                WfExceptionCode.USER_TASK_BLANK.getCode()
        );
        return ruTaskUser;
    }


    public static HiRuTask checkAndGetHiRuTask(Long taskId) {
        HiRuTask hiRuTask = CommandApplications.getHiRuTaskService().selectFirstByTaskId(taskId);
        Assert.isTrue(hiRuTask != null,
                WfExceptionCode.HI_TASK_BLANK.getMsg(),
                WfExceptionCode.HI_TASK_BLANK.getCode()
        );
        return hiRuTask;
    }


    public static RuProcinst checkAndGetRuProcinst(Long id, String from) {
        RuProcinst ruProcinst = CommandApplications.getProcinstService().selectByPrimaryKey(id);
        Assert.isTrue(ruProcinst != null,
                from + WfExceptionCode.PROCINST_BLANK.getMsg(),
                WfExceptionCode.PROCINST_BLANK.getCode()
        );
        return ruProcinst;
    }

    public static RuProcinst checkAndGetRuProcinstFromAll(Long procinstId) {
        RuProcinst ruProcinst = CommandApplications.getProcinstService().selectByPrimaryKey(procinstId);
        if (ruProcinst == null) {
            HiRuProcinst hiRuProcinst = CommandApplications.getHiRuProcinstService().selectByProcInstId(procinstId);

            Assert.isTrue(hiRuProcinst != null,
                    WfExceptionCode.HI_RU_PROCINST_BLANK.getMsg(),
                    WfExceptionCode.HI_RU_PROCINST_BLANK.getCode()
            );
            return Convert.INSTANCE.hiRuProcinstToRuProcinst(hiRuProcinst);
        }
        return ruProcinst;
    }

    public static RuProcinst checkAndGetProcinstByKey(String key) {
        RuProcinst ruProcinst = CommandApplications.getProcinstService().selectByKey(key);
        Assert.isTrue(ruProcinst != null,
                WfExceptionCode.PROCINST_BLANK.getMsg(),
                WfExceptionCode.PROCINST_BLANK.getCode()
        );
        return ruProcinst;
    }


    public static RuActinst checkAndGetRuActinst(Long actinstId) {
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(actinstId);
        Assert.isTrue(ruActinst != null,
                WfExceptionCode.ACTINST_BLANK.getMsg(),
                WfExceptionCode.ACTINST_BLANK.getCode()
        );
        return ruActinst;
    }

    /**
     * 拿到流程信息
     */
    public static ReProcess checkAndGetReProcess(String procKey, String from) {
        ReProcess reProcess
                = CommandApplications.getReProcessService()
                .selectFirstByProcKeyOrderByProcVersionDesc(procKey);

        Assert.isTrue(
                reProcess != null,
                from + WfExceptionCode.PROCESS_RE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_RE_BLANK.getCode()
        );
        return reProcess;
    }

    /**
     * 获取表单模型
     */
    public static ReFormModel checkAndGetReFormModel(Long id, String errorMsg) {

        ReFormModel reFormModel = CommandApplications.getReFormModelService()
                .selectByPrimaryKey(id);

        Assert.isTrue(
                reFormModel != null,
                errorMsg + WfExceptionCode.RE_FORM_MODEL_EMPTY.getMsg(),
                WfExceptionCode.RE_FORM_MODEL_EMPTY.getCode()
        );
        return reFormModel;
    }

    public static DeProcess checkAndGetDeProcess(String procKey, String errorMsg) {
        DeProcess deProcess
                = CommandApplications.getDeProcessService()
                .selectFirstByProcKey(procKey);

        Assert.isTrue(
                deProcess != null,
                errorMsg + WfExceptionCode.PROCESS_DE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_DE_BLANK.getCode()
        );
        return deProcess;
    }

    /**
     * 拿到表单模型
     */
    public static ReFormModel getReFormModelById(Long id) {
        if (id == null) {
            return null;
        }
        return CommandApplications
                .getReFormModelService()
                .selectByPrimaryKey(id);
    }


    public static void deleteTaskByProcInstId(Long procinstId,
                                              String taskDesc,
                                              UserTaskStatusEnum statusEnum
    ) {
        if (procinstId == null) {
            return;
        }
        List<RuTask> ruTasks = CommandApplications.getRuTaskService().selectByProcInstId(procinstId);
        for (RuTask ruTask : ruTasks) {
            ruTask.setStatus(RuTaskStatusEnum.DONE.getStatus());
        }
        if (Utils.isNotEmpty(ruTasks)) {
            List<HiRuTask> hiRuTasks = Convert.INSTANCE.ruTasksToHiRuTasks(ruTasks);
            CommandApplications.getHiRuTaskService().batchInsert(hiRuTasks);
            CommandApplications.getRuTaskService().deleteByProcInstId(procinstId);
            for (RuTask ruTask : ruTasks) {
                saveRuTaskOperateLog(ruTask.getId(), taskDesc);
            }
        }
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByProcinstIdId(procinstId);
        if (Utils.isNotEmpty(ruTaskUsers)) {
            batchConvertRuTaskUsersToHis(ruTaskUsers, statusEnum);
            CommandApplications.getTaskUserService().deleteByProcInstId(procinstId);
        }

    }


    /**
     * ru 转移到 hi
     * 完成待办
     */
    public static void batchConvertRuTaskUsersToHis(List<RuTaskUser> ruTaskUsers,
                                                    UserTaskStatusEnum statusEnum) {
        List<HiRuTaskUser> hiRuTaskUsers = Convert.INSTANCE.ruTaskUsersTohiRuTaskUsers(ruTaskUsers);
        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
            hiRuTaskUser.setTaskDesc(statusEnum.getDesc());
            hiRuTaskUser.setGmtModified(System.currentTimeMillis());
//            hiRuTaskUser.setFlags(flagsEnum.getFlag());
            hiRuTaskUser.setUserTaskId(hiRuTaskUser.getId());
            hiRuTaskUser.setStatus(statusEnum.getStatus());
        }
        CommandApplications.getHiRuTaskUserService().batchInsert(hiRuTaskUsers);
    }


    public static void convertRuTaskUsersToHis(RuTaskUser ruTaskUser,
                                               String comment,
                                               UserTaskStatusEnum statusEnum) {
        HiRuTaskUser hiRuTaskUser = Convert.INSTANCE.ruTaskUserToHiRuTaskUser(ruTaskUser);
        hiRuTaskUser.setGmtEnd(System.currentTimeMillis());
        hiRuTaskUser.setTaskDesc(statusEnum.getDesc());
        hiRuTaskUser.setStatus(statusEnum.getStatus());
        hiRuTaskUser.setUserTaskId(ruTaskUser.getId());
//        hiRuTaskUser.setFlags(hiRuTaskUser.getFlags() + finishedFlags);
        hiRuTaskUser.setComment(comment);
        HiRuTaskUser dbHiRuTaskUser = CommandApplications.getHiRuTaskUserService()
                .selectFirstByUserTaskId(ruTaskUser.getId());
        if (dbHiRuTaskUser != null) {
            hiRuTaskUser.setId(dbHiRuTaskUser.getId());
            CommandApplications.getHiRuTaskUserService().updateByPrimaryKey(hiRuTaskUser);
        } else {
            CommandApplications.getHiRuTaskUserService().insert(hiRuTaskUser);
        }
        List<HiRuTaskUser> hiRuTaskUsers = new ArrayList<>(1);
        hiRuTaskUsers.add(hiRuTaskUser);
    }


    public static void signActStatusFinishedByPrcInstId(Long procinstId) {
        CommandApplications.getActinstService()
                .updateStatusByProcInstId(ActinstStatusEnum.FINISHED.getCode(), procinstId);
    }


    public static void delUserTask(RuTaskUser ruTaskUser,
                                   String comment,
                                   UserTaskStatusEnum statusEnum) {
        // 待办相关的
        convertRuTaskUsersToHis(ruTaskUser, comment, statusEnum);
        // 删除 run_task的任务
        CommandApplications.getTaskUserService().deleteByPrimaryKey(ruTaskUser.getId());
    }


    /**
     * 激活前加签任务
     *
     * @param userTaskId 用户任务id
     */
    private static void activeBeforeSignTaskUser(Long userTaskId) {
        HiRuTaskUser hiRuTaskUser = CommandApplications.getHiRuTaskUserService().selectFirstByUserTaskId(userTaskId);
        Assert.isTrue(hiRuTaskUser != null,
                WfExceptionCode.NEED_ACTIVE_USER_TASK_BLANK.getMsg(),
                WfExceptionCode.NEED_ACTIVE_USER_TASK_BLANK.getCode()
        );
        RuTask ruTask = CommandApplications.getRuTaskService().checkAndGetRuTaskFromAll(hiRuTaskUser.getTaskId());
        RuActinst ruActinst = CommandApplications.getActinstService().selectByPrimaryKey(ruTask.getActinstId());
        ChildShape childShape = ProcConfHelper.getChildShapeByActJson(ruActinst.getVarJson());
        BeforSignBackTypeEnum beforSignBackType = null;
        SignConfig signConfig = childShape.getSignConfig();
        if (signConfig != null) {
            beforSignBackType = BeforSignBackTypeEnum.getByCode(signConfig.getBeforeSignBackType());
        }
        if (beforSignBackType == null) {
            beforSignBackType = BeforSignBackTypeEnum.LEVEL_BACK;
        }

        if (UserTaskStatusEnum.AUTO_COMPLETE.hasTag(hiRuTaskUser.getFlags())) {
            hiRuTaskUser.setFlags(hiRuTaskUser.getFlags() - UserTaskStatusEnum.AUTO_COMPLETE.getStatus());
        }
        RuTaskUser activeRuTaskUser = Convert.INSTANCE.hiRuTaskUserToRuTaskUser(hiRuTaskUser);
        //默认都是逐级退回,退回到顶级需要递归查找最初的加签人
        if (BeforSignBackTypeEnum.TOP_BACK.equals(beforSignBackType)) {
            HiRuTaskUser topHiUserTask = findBeforeSignTopHiUserTask(hiRuTaskUser.getTaskId(), userTaskId);
            if (UserTaskStatusEnum.AUTO_COMPLETE.hasTag(topHiUserTask.getFlags())) {
                topHiUserTask.setFlags(topHiUserTask.getFlags() - UserTaskStatusEnum.AUTO_COMPLETE.getStatus());
            }
            activeRuTaskUser = Convert.INSTANCE.hiRuTaskUserToRuTaskUser(topHiUserTask);
        }
        activeRuTaskUser.setId(null);
        activeRuTaskUser.setStatus(0);
        activeRuTaskUser.setTaskDesc("前加签任务恢复");
        activeRuTaskUser.setComment(null);
        List<RuTaskUser> ruTaskUsers = new ArrayList<>(1);
        ruTaskUsers.add(activeRuTaskUser);
        //保存激活的任务
        String userTaskTitle = CommandContextFactory.getCommandContext().getRuTask().getTaskName();
        saveRuTaskUsers(activeRuTaskUser.getFlags(), ruTaskUsers, userTaskTitle, false);
    }

    private static HiRuTaskUser findBeforeSignTopHiUserTask(Long taskId, Long userTaskId) {
        List<HiRuTaskUser> hiRuTaskUsers = CommandApplications.getHiRuTaskUserService()
                .selectByTaskIdIn(Lists.newArrayList(taskId));
        Map<Long, Long> idAndPidRelMap = new HashMap<>();
        Map<Long, HiRuTaskUser> hiRuTaskUserMap = new HashMap<>();
        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
            idAndPidRelMap.put(hiRuTaskUser.getUserTaskId(), hiRuTaskUser.getPid());
            hiRuTaskUserMap.put(hiRuTaskUser.getUserTaskId(), hiRuTaskUser);
        }
        Long pid = idAndPidRelMap.get(userTaskId);
        while (pid != null) {
            userTaskId = pid;
            pid = idAndPidRelMap.get(pid);
        }
        return hiRuTaskUserMap.get(userTaskId);
    }

    /**
     * 激活个签任务
     *
     * @param userTaskId 用户任务id
     */
    private static void activeIndividualSignTaskUser(Long userTaskId) {
        RuTaskUser ruTaskUser = CommandApplications.getTaskUserService().selectByPrimaryKey(userTaskId);
        Assert.isTrue(ruTaskUser != null,
                WfExceptionCode.NEED_ACTIVE_USER_TASK_BLANK.getMsg(),
                WfExceptionCode.NEED_ACTIVE_USER_TASK_BLANK.getCode()
        );
        String taskName = CommandApplications.getRuTaskService().selectByPrimaryKey(ruTaskUser.getTaskId())
                .getTaskName();
        List<RuTaskUser> ruTaskUserList = new ArrayList<>(1);
        ruTaskUserList.add(ruTaskUser);
    }


    public static void delUserTaskAndSendCopyTask(RuTaskUser ruTaskUser,
                                                  String comment,
                                                  UserTaskStatusEnum statusEnum) {
        delUserTask(ruTaskUser, comment, statusEnum);
    }


    public static void delUserTaskByTaskId(Long taskId, UserTaskStatusEnum statusEnum) {
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByTaskId(taskId);
        batchConvertRuTaskUsersToHis(ruTaskUsers, statusEnum);
        CommandApplications.getTaskUserService().deleteByTaskId(taskId);
    }


    public static void delUserTaskByPid(Long userTaskPid, UserTaskStatusEnum statusEnum) {
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByPid(userTaskPid);
        batchConvertRuTaskUsersToHis(ruTaskUsers, statusEnum);
        List<Long> userTaskIds = ruTaskUsers.stream().map(RuTaskUser::getId).collect(Collectors.toList());
        CommandApplications.getTaskUserService().deleteByIdIn(userTaskIds);
    }


    public static void delUserTaskByProcinstId(Long procinstId, String taskDesc) {
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByProcinstIdId(procinstId);
        batchConvertRuTaskUsersToHis(ruTaskUsers, UserTaskStatusEnum.AUTO_COMPLETE);
        CommandApplications.getTaskUserService().deleteByProcInstId(procinstId);
    }


    public static List<RuTaskUser> checkAndGetRuTaskUsersByTaskId(Long taskId) {
        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByTaskId(taskId);
        Assert.isTrue(!Utils.isEmpty(ruTaskUsers),
                WfExceptionCode.USER_TASK_BLANK.getMsg(),
                WfExceptionCode.USER_TASK_BLANK.getCode()
        );
        return ruTaskUsers;
    }

    public static String runFirstActinst(Long startActId,
                                         Operator operator,
                                         List<ManualStartInfo> manualStartInfos,
                                         String procinstVarJson,
                                         String comment) {
        Collection<Assignee> assignees = new HashSet<>();
        assignees.add(Convert.INSTANCE.operatorToAssignee(operator));
        RuTaskUser ruTaskUser = RunTimeManager.createTaskAndUserTasksAndReturnFirstUserTask(startActId,
                assignees,
                TaskOptionTypeEnum.START_PROC.getDesc(),
                UserTaskFlagsEnum.OR_SIGN.getFlag() + UserTaskFlagsEnum.MY_START.getFlag(), false, false);
        CommandContextFactory.getCommandContext().setStarterUserTaskKey(ruTaskUser.getUserTaskKey());
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        Logs.info("[RunTimeManager.runFirstActinst] startActId: {}, manualStartInfos: {}, ruTaskUser: {}", startActId, manualStartInfos, ruTaskUser);
        if (Utils.isNotEmpty(manualStartInfos)) {
            // 组装节点的信息
            List<ManualCompleteInfo> manualCompleteInfos = startManualToComplete(manualStartInfos);

            RunTimeManager.manualFinishTaskAndLeave(ruTaskUser,
                    TaskOptionTypeEnum.START_PROC.getDesc(),
                    comment,
                    manualCompleteInfos, procinstVarJson);
        } else {
            RunTimeManager.autoFinishTaskAndLeave(false, ruTaskUser,
                    TaskOptionTypeEnum.START_PROC.getDesc(),
                    comment,
                    procinstVarJson, null);
        }
        return ruTaskUser.getUserTaskKey();
    }

    /**
     * 启动手动参数转换成完成手动参数
     */
    private static List<ManualCompleteInfo> startManualToComplete(List<ManualStartInfo> manualStartInfos) {
        List<ManualCompleteInfo> manualCompleteInfos = new ArrayList<>(manualStartInfos.size());
        ReProcess reProcess = CommandContextFactory.getCommandContext().getReProcess();
        for (ManualStartInfo manualStartInfo : manualStartInfos) {
            ManualCompleteInfo manualCompleteInfo = new ManualCompleteInfo();
            //节点
            String actKey = manualStartInfo.getActKey();
            if (Utils.isEmpty(actKey)) {
                continue;
            }
            //节点处理人
//            ChildShapeAssigneeInfo assigneeInfo = new ChildShapeAssigneeInfo();
            ChildShape childShape = ProcConfHelper.getChildShapeByActKeyAndProcJson(actKey, reProcess.getProcJson());
            Assert.isTrue(childShape != null,
                    WfExceptionCode.ILLEGAL_ACT_KEY.getMsg(),
                    WfExceptionCode.ILLEGAL_ACT_KEY.getCode()
            );
//            ChildShapeAssigneeInfo assigneeInfo1 = manualStartInfo.getAssigneeInfo();
//            if (assigneeInfo1 != null) {
//                Collection<Assignee> eachActAssignees = assigneeInfo1.getAssignees();
////                assigneeInfo.setAssignees(eachActAssignees);
//                manualCompleteInfo.setAssignees(manualStartInfo.getAssignees());
//            }
            manualCompleteInfo.setAssignees(manualStartInfo.getAssignees());
            // 查询节点信息
//            Long actinstIdByActKey = getActinstIdByActKey(actKey);
//            if (actinstIdByActKey == null) {
//                continue;
//            }
            manualCompleteInfo.setActKey(actKey);
            manualCompleteInfos.add(manualCompleteInfo);
        }
        return manualCompleteInfos;
    }


    public static Long getActinstIdByActKey(String actInstKey) {
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        if (actInstKey != null) {
            return CommandApplications.getActinstService()
                    .selectFirstIdByActinstKeyAndProcinstId(actInstKey, ruProcinst.getId());
        }
        return null;
    }

    public static String getTaskTitle(String varJson, String taskTitle) {
        if (taskTitle == null || "".equals(taskTitle)) {
            taskTitle = "无标题";
        } else {
            //
            if (taskTitle.startsWith("${#")) {
                taskTitle = WfUtils.getJsonVal(varJson, taskTitle);
                if ("".equals(taskTitle)) {
                    taskTitle = "无标题";
                }
            }
        }
        return taskTitle;
    }


    public static void checkOperator(String assigneeId, String assigneeOrgId, Operator operator) {
        // 解决多部门 发多个待办的问题 产品说先去掉 在看看会出现什么问题
        Assert.isTrue(assigneeId.equals(operator.getId()),
                WfExceptionCode.OPERATOR_ERROR.getMsg(),
                WfExceptionCode.OPERATOR_ERROR.getCode()
        );
    }


    public static void checkSubUserTask(Long userTaskId) {
        List<Long> sonUserTaskIds = CommandApplications.getTaskUserService().selectIdByPid(userTaskId);
        Assert.isTrue(Utils.isEmpty(sonUserTaskIds),
                WfExceptionCode.EXIST_SUB_TASK_ERROR.getMsg(),
                WfExceptionCode.EXIST_SUB_TASK_ERROR.getCode()
        );
    }

    public static HiRuTaskUser checkAndGetHiRuTaskUser(Long userTaskId) {
        HiRuTaskUser hiRuTaskUser = CommandApplications.getHiRuTaskUserService().selectFirstByUserTaskId(userTaskId);

        Assert.isTrue(hiRuTaskUser != null,
                WfExceptionCode.HI_USER_TASK_NULL.getMsg(),
                WfExceptionCode.HI_USER_TASK_NULL.getCode()
        );
        return hiRuTaskUser;
    }


    public static void createCopyUserTasks(RuTask ruTask,
                                           Collection<Assignee> assignees,
                                           String userTaskTitle,
                                           int userTaskFlags) {
//        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();
        if (Utils.isEmpty(assignees)) {
            return;
        }
//        if ((userTaskFlags & 1) != 0) {
//            userTaskFlags -= 1;
//        }
        List<RuTaskUser> ruTaskUsers = RunTimeManager.getRnUserTasksByAssigneesAndSaveAgent(
                assignees, UserTaskFlagsEnum.COPY.getDesc(),
                ruTask.getId(),
                UserTaskFlagsEnum.COPY.getFlag()
        );
        //插入ru >> 解决下面的id冲突问题
//        CommandApplications.getTaskUserService().batchInsert(ruTaskUsers);
        saveRuTaskUsers(UserTaskFlagsEnum.COPY.getFlag(), ruTaskUsers, userTaskTitle, false);
        // 获取所有的id
        List<Long> collect = ruTaskUsers.stream().map(RuTaskUser::getId).collect(Collectors.toList());

        List<HiRuTaskUser> hiRuTaskUsers = Convert.INSTANCE.ruTaskUsersTohiRuTaskUsers(ruTaskUsers);
        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
            // 0待办，1已办
            hiRuTaskUser.setStatus(0);
        }
        CommandApplications.getHiRuTaskUserService().batchInsert(hiRuTaskUsers);
        // 抄送用户任务 是直接到hi表中 所以这里是循环 hi到主键ID 直接放到set 到 user_task_id 字段 此方法有巨大bug如下解释
        // 抄送直接去hi表  用hi的主键赋值user_task_id 会导致 user_task_id冲突 会出现 不是唯一的字段 (user_task_id 不能以hi表的id 和ru表的id 一起使用，非唯一的)
        // 需要先插入ru 然后在拆入hi
//        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
//            hiRuTaskUser.setUserTaskId(hiRuTaskUser.getId());
//        }

        // 删除此抄送节点的ru任务  抄送是不需要去ru的 先插入再删除也只是为了 取主键id
        CommandApplications.getTaskUserService().deleteByIdIn(collect);

        CommandApplications.getHiRuTaskUserService().updateBatch(hiRuTaskUsers);
//        CommandApplications.getRemoteService().batchInsertCopyToTodoCenter(hiRuTaskUsers, userTaskTitle);
    }

    public static Long checkAndGetTaskUserIdByTaskUserKeyFromAll(String userTaskKey) {
        return CommandApplications.getTaskUserService().checkAndGetTaskUserIdByTaskUserKeyFromAll(userTaskKey);
    }

    public static Set<NodeAssignee> getAssigneesFromNodes(List<String> formNodes1, Long procinstId) {
        List<RuActinst> ruActinsts = CommandApplications.getActinstService()
                .selectByActinstKeyInAndProcinstId(formNodes1, procinstId);
        if (Utils.isEmpty(ruActinsts)) {
            return new HashSet<>();
        }
        List<Long> actinstIds = ruActinsts.stream().map(RuActinst::getId).collect(Collectors.toList());
        List<RuTask> ruTasks = CommandApplications.getRuTaskService().selectByActinstIdIn(actinstIds);
        Map<Long, Long> rutaskIdAndActid = ruTasks.stream()
                .collect(Collectors.toMap(RuTask::getId, RuTask::getActinstId));
        List<HiRuTask> hiRuTasks = CommandApplications.getHiRuTaskService().selectByActinstIdIn(actinstIds);
        Map<Long, Long> hiTaskIdAndActId = hiRuTasks.stream()
                .collect(Collectors.toMap(HiRuTask::getTaskId, HiRuTask::getActinstId));
        Set<Long> allTaskIds = new HashSet<>(1024);
        allTaskIds.addAll(ruTasks.stream().map(RuTask::getId).collect(Collectors.toSet()));
        Set<Long> hiTaskIds = hiRuTasks.stream().map(HiRuTask::getTaskId).collect(Collectors.toSet());
        allTaskIds.addAll(hiTaskIds);

        List<RuTaskUser> ruTaskUsers = CommandApplications.getTaskUserService().selectByTaskIdIn(allTaskIds);
        List<HiRuTaskUser> hiRuTaskUsers = CommandApplications.getHiRuTaskUserService().selectByTaskIdIn(allTaskIds);
        Map<Long, RuActinst> idAndAct = ruActinsts.stream()
                .collect(Collectors.toMap(RuActinst::getId, Function.identity()));
        Set<NodeAssignee> res = new HashSet<>(1024);
        if (Utils.isNotEmpty(ruTaskUsers)) {
            for (RuTaskUser ruTaskUser : ruTaskUsers) {
                if (ruTaskUser == null) {
                    continue;
                }
                NodeAssignee assignee = ruTaskUserToNodeAssignee(ruTaskUser);
                Long actinstId = rutaskIdAndActid.get(ruTaskUser.getTaskId());
                if (actinstId != null) {
                    RuActinst ruActinst = idAndAct.get(actinstId);
                    if (ruActinst != null) {
                        assignee.setActinstName(ruActinst.getActinstName());
                    }
                }
                res.add(assignee);
            }
        }
        if (Utils.isNotEmpty(hiRuTaskUsers)) {
            for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
                if (hiRuTaskUser == null) {
                    continue;
                }
                NodeAssignee assignee = hiRuTaskUserToNodeAssignee(hiRuTaskUser);
                Long actinstId = rutaskIdAndActid.get(hiRuTaskUser.getTaskId());
                if (actinstId == null) {
                    actinstId = hiTaskIdAndActId.get(hiRuTaskUser.getTaskId());
                }
                if (actinstId != null) {
                    RuActinst ruActinst = idAndAct.get(actinstId);
                    if (ruActinst != null) {
                        assignee.setActinstName(ruActinst.getActinstName());
                    }
                }
                res.add(assignee);
            }
        }
        // 去重 解决节点回退的时候出现了2个一样的用户，其他一个用户没有手机号 导致无法去重的问题 >> 根据用户id去重
        res = res.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(NodeAssignee::getId))), HashSet::new));
        return res;
    }

    static NodeAssignee ruTaskUserToNodeAssignee(RuTaskUser ruTaskUser) {
        NodeAssignee assignee = new NodeAssignee();
        assignee.setId(ruTaskUser.getAssigneeId());
        assignee.setName(ruTaskUser.getAssigneeName());
        assignee.setDeptId(ruTaskUser.getAssigneeDeptId());
        assignee.setDeptName(ruTaskUser.getAssigneeDeptName());
        assignee.setOrgId(ruTaskUser.getAssigneeOrgId());
        assignee.setOrgName(ruTaskUser.getAssigneeOrgName());
        assignee.setMobile(ruTaskUser.getAssigneeMobile());
        return assignee;
    }

    static NodeAssignee hiRuTaskUserToNodeAssignee(HiRuTaskUser ruTaskUser) {
        NodeAssignee assignee = new NodeAssignee();
        assignee.setId(ruTaskUser.getAssigneeId());
        assignee.setName(ruTaskUser.getAssigneeName());
        assignee.setDeptId(ruTaskUser.getAssigneeDeptId());
        assignee.setDeptName(ruTaskUser.getAssigneeDeptName());
        assignee.setOrgId(ruTaskUser.getAssigneeOrgId());
        assignee.setOrgName(ruTaskUser.getAssigneeOrgName());
        assignee.setMobile(ruTaskUser.getAssigneeMobile());
        return assignee;
    }

    /**
     * 查询分发节点网关上下存在的没有完成分发的任务数量
     */
    public static Long selectNotFinishReadTaskByAct(Long readGateWayId, Long procinstId, SearchModelEnum modelEnum) {
        if (readGateWayId == null) {
            return null;
        }
        if (procinstId == null) {
            return null;
        }
        Set<String> filterType = new HashSet<>();
        filterType.add(ActInstTypeEnum.READ.getType());
        filterType.add(ActInstTypeEnum.READ_GATEWAY.getType());
        //查找分发节点上下游节点信息
        List<Long> readActinstIds = findActIdBySearchModel(readGateWayId, procinstId, modelEnum, filterType);
        if (readActinstIds == null) return null;
        Set<Long> taskIds = CommandApplications.getRuTaskService().selectIdByActinstIdIn(readActinstIds);
        //返回找到的未任务数量
        return CommandApplications.getTaskUserService()
                .countByTaskIdInAndStatusNot(taskIds, UserTaskStatusEnum.READ_FINISHED.getStatus());
    }

    /**
     * 根据查找方向查找节点信息
     */
    public static List<Long> findActIdBySearchModel(Long startActId,
                                                    Long procinstId,
                                                    SearchModelEnum modelEnum,
                                                    Set<String> filterType) {
        //流程实例上的节点和位置信息
        List<RuActinstSite> ruActinstSites = CommandApplications.getActinstSiteService().selectByProInstId(procinstId);
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectByProcInstId(procinstId);
        if (Utils.isEmpty(ruActinstSites) || Utils.isEmpty(ruActinsts)) {
            return null;
        }
        Map<Long, String> actIdAndTypeRel = ruActinsts.stream()
                .collect(Collectors.toMap(RuActinst::getId, RuActinst::getActinstType));
        List<Long> actinstIds = new ArrayList<>();
        if (SearchModelEnum.UP.equals(modelEnum)) {
            initActinstIdsUp(startActId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
        } else if (SearchModelEnum.DOWN.equals(modelEnum)) {
            initActinstIdsDown(startActId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
        } else {
            initActinstIdsUp(startActId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
            initActinstIdsDown(startActId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
        }
        return actinstIds;
    }

    /**
     * 从actId向上找节点id存入actinstIds
     *
     * @param actId           查找开始位置
     * @param actinstIds      结果集合
     * @param ruActinstSites  所有的节点位置
     * @param actIdAndTypeRel 节点位置和类型映射
     * @param filterType      只要filterType里面的类型节点
     */
    public static void initActinstIdsUp(Long actId,
                                        List<Long> actinstIds,
                                        List<RuActinstSite> ruActinstSites,
                                        Map<Long, String> actIdAndTypeRel,
                                        Set<String> filterType
    ) {
        String type = actIdAndTypeRel.get(actId);
        if (Utils.isNotEmpty(filterType) && !filterType.contains(type)) {
            //不要指定的节点类型
            return;
        }
        //查询actId的源节点信息
        Set<Long> sourceActinstIds = findSourceActinstIds(actId, ruActinstSites);
        if (Utils.isNotEmpty(filterType)) {
            sourceActinstIds = sourceActinstIds
                    .stream()
                    .filter(a -> ActInstTypeEnum.READ.getType().equals(actIdAndTypeRel.get(a)) ||
                            ActInstTypeEnum.READ_GATEWAY.getType().equals(actIdAndTypeRel.get(a)))
                    .collect(Collectors.toSet());
        }
        for (Long sourceActinstId : sourceActinstIds) {
            //当分发网关找到自己时，说明重复了，不用在找了
            if (actinstIds.contains(sourceActinstId)) {
                return;
            }
        }
        //返回找到的节点信息
        actinstIds.addAll(sourceActinstIds);
        for (Long sourceActinstId : sourceActinstIds) {
            initActinstIdsUp(sourceActinstId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
        }
    }

    public static void initActinstIdsDown(Long actId,
                                          List<Long> actinstIds,
                                          List<RuActinstSite> ruActinstSites,
                                          Map<Long, String> actIdAndTypeRel,
                                          Set<String> filterType) {
        String type = actIdAndTypeRel.get(actId);
        if (Utils.isNotEmpty(filterType) && !filterType.contains(type)) {
            //不要指定的节点类型
            return;
        }
        //查询actId的目标节点信息
        Set<Long> targetActinstIds = findTargetActinstIds(actId, ruActinstSites);
        if (Utils.isNotEmpty(filterType)) {
            targetActinstIds = targetActinstIds
                    .stream()
                    .filter(a -> ActInstTypeEnum.READ.getType().equals(actIdAndTypeRel.get(a)) ||
                            ActInstTypeEnum.READ_GATEWAY.getType().equals(actIdAndTypeRel.get(a)))
                    .collect(Collectors.toSet());
        }
        for (Long targetActId : targetActinstIds) {
            //当分发网关找到自己时，说明重复了，不用在找了
            if (actinstIds.contains(targetActId)) {
                return;
            }
        }
        //返回找到的节点信息
        actinstIds.addAll(targetActinstIds);
        for (Long sourceActinstId : targetActinstIds) {
            initActinstIdsDown(sourceActinstId, actinstIds, ruActinstSites, actIdAndTypeRel, filterType);
        }
    }

//    private static Long findTargetActinstIds(Long actinstId, List<RuActinstSite> ruActinstSites) {
//        for (RuActinstSite ruActinstSite : ruActinstSites) {
//            if (ruActinstSite.getActinstId().equals(actinstId)) {
//                return ruActinstSite.getTargetId();
//            }
//        }
//        return null;
//    }

    /**
     * 查找actinstId的
     *
     * @param actinstId      当前节点
     * @param ruActinstSites 位置信息
     * @return
     */
    private static Set<Long> findSourceActinstIds(Long actinstId, List<RuActinstSite> ruActinstSites) {
        Set<Long> res = new HashSet<>(16);
        for (RuActinstSite ruActinstSite : ruActinstSites) {
            if (ruActinstSite.getTargetId() == null) {
                continue;
            }
            //目标节点是当前节点的线
            if (ruActinstSite.getTargetId().equals(actinstId)) {
                //线的目标节点
                Long sourceActinstId = findSourceActinstIdsForSeq(ruActinstSite.getActinstId(), ruActinstSites);
                if (sourceActinstId != null) {
                    res.add(sourceActinstId);
                }
            }
        }
        return res;
    }

    /**
     * 查找actinstId的目标节点
     *
     * @param actinstId      当前节点
     * @param ruActinstSites 位置信息
     * @return
     */
    private static Set<Long> findTargetActinstIds(Long actinstId, List<RuActinstSite> ruActinstSites) {
        Set<Long> res = new HashSet<>(16);
        for (RuActinstSite ruActinstSite : ruActinstSites) {
            if (ruActinstSite.getTargetId() == null) {
                continue;
            }
            //源节点是当前节点的线
            if (ruActinstSite.getActinstId().equals(actinstId)) {
                //线的目标节点
                Long sourceActinstId = findTargetActinstIdsForSeq(ruActinstSite.getTargetId(), ruActinstSites);
                if (sourceActinstId != null) {
                    res.add(sourceActinstId);
                }
            }
        }
        return res;
    }

    /**
     * 返回线的源节点
     *
     * @param actinstId      线id
     * @param ruActinstSites 位置信息
     * @return 返回线的目标节点
     */
    private static Long findSourceActinstIdsForSeq(Long actinstId, List<RuActinstSite> ruActinstSites) {
        for (RuActinstSite ruActinstSite : ruActinstSites) {
            if (ruActinstSite.getTargetId() == null) {
                continue;
            }
            if (ruActinstSite.getTargetId().equals(actinstId)) {
                return ruActinstSite.getActinstId();
            }
        }
        return null;
    }

    /**
     * 返回线的目标节点
     *
     * @param actinstId      线id
     * @param ruActinstSites 位置信息
     * @return 返回线的目标节点
     */
    private static Long findTargetActinstIdsForSeq(Long actinstId, List<RuActinstSite> ruActinstSites) {
        for (RuActinstSite ruActinstSite : ruActinstSites) {
            if (ruActinstSite.getActinstId() == null) {
                continue;
            }
            if (ruActinstSite.getActinstId().equals(actinstId)) {
                return ruActinstSite.getTargetId();
            }
        }
        return null;
    }

    /**
     * 初始化流程实例相关信息
     *
     * @param actChildShapesList 需要初始化的新元素列表,对应节点实例
     */
    public static List<RuActinst> initActinstsAndBack(List<ChildShape> actChildShapesList,
                                                      Map<String, Long> convertActIdAndKey
    ) {
//        final Long procinstid = procinst.getId();
        //节点信息
        Assert.isTrue(
                Utils.isNotEmpty(actChildShapesList),
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getCode()
        );
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        //节点
        List<RuActinst> ruActinsts = getActinstByProcinstAndActChilds(actChildShapesList, convertActIdAndKey);
        //插入节点
        CommandApplications.getActinstService().batchInsert(ruActinsts);

        //重新查一次节点
        List<RuActinst> lastRuActinsts = CommandApplications.getActinstService().selectByProcInstId(ruProcinst.getId());
        Map<String, Long> actIdAndKey = lastRuActinsts
                .stream()
                .collect(Collectors.toMap(RuActinst::getActinstKey, RuActinst::getId));
//        Map<String, Long> actKeyAndIdRel = new HashMap<>(actChildShapesList.size());
//
//        for (ChildShape childShape : actChildShapesList) {
//            String resourceId = childShape.getResourceId();
//            actKeyAndIdRel.put(resourceId, actIdAndKey.get(resourceId));
//        }
        //系统任务
//        insertSysTask(procinst, actChildShapesList, actKeyAndIdRel);

        //节点位置
        List<RuActinstSite> ruActinstSites = getActsiteByProcinstAndActChilds(ruProcinst.getId(), actChildShapesList, actIdAndKey);

        //插入节点位置
        CommandApplications.getActinstSiteService().batchInsert(ruActinstSites);

        return lastRuActinsts;
    }

    public static RuActinst getStartRuActinst(List<RuActinst> ruActinsts) {
        for (RuActinst ruActinst : ruActinsts) {
            if (ActInstTypeEnum.START_EVENT.getType().equals(ruActinst.getActinstType())) {
                return ruActinst;
            }
        }
        return null;
    }

    public static List<RuActinst> getActinstByProcinstAndActChilds(
            List<ChildShape> childShapesList,
            Map<String, Long> convertActIdAndKey) {

        List<RuActinst> ruActinsts = new ArrayList<>(childShapesList.size());
        for (ChildShape childShape : childShapesList) {
            RuActinst ruActinst = initRuActinst(childShape);
            //如果已经存在id，则使用已经存在的
            if (Utils.isNotEmpty(convertActIdAndKey)) {
                Long dbId = convertActIdAndKey.get(childShape.getResourceId());
                if (dbId != null) {
                    ruActinst.setId(dbId);
                }
            }
            ruActinsts.add(ruActinst);
        }
        return ruActinsts;
    }

//    private static void insertSysTask(RuProcinst procinst, List<ChildShape> childShapesList, Map<String, Long> actKeyAndIdRel) {
//        List<RuSysTask> sysTasks = new ArrayList<>(1024);
//        for (ChildShape childShape : childShapesList) {
//            if (CollectionUtils.isNotEmpty(childShape.getStartTasks())) {
//                for (int i = 0; i < childShape.getStartTasks().size(); i++) {
//                    ChildShapeSysTask childShapeSysTask = childShape.getStartTasks().get(i);
//                    RuSysTask sysTask = generateRuSysTask(procinst.getId(), procinst.getProcKey(), i, childShapeSysTask,
//                            SysTaskExecLocationEnum.ACTINST_BEFORE);
//                    sysTask.setActinstId(actKeyAndIdRel.get(childShape.getResourceId()));
//                    sysTasks.add(sysTask);
////                    sysTaskMap.put(childShape.getResourceId(), sysTask);
//                }
//            }
//            if (CollectionUtils.isNotEmpty(childShape.getEndTasks())) {
//                for (int i = 0; i < childShape.getEndTasks().size(); i++) {
//                    ChildShapeSysTask childShapeSysTask = childShape.getEndTasks().get(i);
//                    RuSysTask sysTask = generateRuSysTask(procinst.getId(), procinst.getProcKey(), i, childShapeSysTask,
//                            SysTaskExecLocationEnum.ACTINST_AFTER);
//                    sysTask.setActinstId(actKeyAndIdRel.get(childShape.getResourceId()));
//                    sysTasks.add(sysTask);
////                    sysTaskMap.put(childShape.getResourceId(), sysTask);
//                }
//            }
//        }
////        for (String key : sysTaskMap.keySet()) {
////            Long actInstId = actKeyAndIdRel.get(key);
////            List<RuSysTask> sysTasks = sysTaskMap.get(key);
////            sysTasks.forEach(v -> v.setActinstId(actInstId));
////        }
////        CommandApplications.getSysTaskService().batchInsertRuSysTasks(sysTaskMap.values());
//        CommandApplications.getSysTaskService().batchInsertRuSysTasks(sysTasks);
//    }

    private static List<RuActinstSite> getActsiteByProcinstAndActChilds(Long procinstid, List<ChildShape> childShapesList, Map<String, Long> actKeyAndIdRel) {
        List<RuActinstSite> ruActinstSites = new ArrayList<>(childShapesList.size());
        for (ChildShape childShape : childShapesList) {
            String resourceId = childShape.getResourceId();
            List<ChildShapeOutgoing> outgoings = childShape.getOutgoing();
            if (outgoings != null) {
                for (ChildShapeOutgoing outgoing : outgoings) {
                    String outResourceId = outgoing.getResourceId();
                    RuActinstSite ruActinstSite = new RuActinstSite();
                    ruActinstSite.setGmtCreate(System.currentTimeMillis());
                    ruActinstSite.setGmtModified(System.currentTimeMillis());
                    ruActinstSite.setActinstId(actKeyAndIdRel.get(resourceId));
                    ruActinstSite.setProInstId(procinstid);
                    ruActinstSite.setTargetId(actKeyAndIdRel.get(outResourceId));
                    ruActinstSites.add(ruActinstSite);
                }
            }
        }
        return ruActinstSites;
    }

    public static RuActinst initRuActinst(ChildShape childShape) {
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();

        RuActinst ruActinst = new RuActinst();
        ruActinst.setGmtModified(System.currentTimeMillis());
        ruActinst.setGmtCreate(System.currentTimeMillis());
        //节点属性
        ChildShapeProperties properties = childShape.getProperties();

        Assert.isTrue(
                properties != null,
                WfExceptionCode.ACT_PROPERTIES_BLANK.getMsg(),
                WfExceptionCode.ACT_PROPERTIES_BLANK.getCode()
        );
        String name = properties.getName();
        Assert.isTrue(
                Utils.isNotEmpty(name),
                WfExceptionCode.ACT_PROPERTIES_NAME_BLANK.getMsg(),
                WfExceptionCode.ACT_PROPERTIES_NAME_BLANK.getCode()
        );
        //默认节点实例标题为 发起人姓名+‘的’+流程名+'-'节点名称
        String starterName = ruProcinst.getStarterName();
        String procName = ruProcinst.getProcName();
        ruActinst.setActinstName(starterName + "的" + procName + "-" + name);
        String resourceId = childShape.getResourceId();
        Assert.isTrue(
                Utils.isNotEmpty(resourceId),
                WfExceptionCode.ACT_PROPERTIES_RESOURCE_ID_BLANK.getMsg(),
                WfExceptionCode.ACT_PROPERTIES_RESOURCE_ID_BLANK.getCode()
        );
        ruActinst.setActinstKey(resourceId);
        ruActinst.setProcinstId(ruProcinst.getId());
        ruActinst.setStartTime(System.currentTimeMillis());
        String actType = childShape.getActType();
        Assert.isTrue(
                Utils.isNotEmpty(actType),
                WfExceptionCode.ACT_PROPERTIES_ACT_TYPE_BLANK.getMsg(),
                WfExceptionCode.ACT_PROPERTIES_ACT_TYPE_BLANK.getCode()
        );
        ruActinst.setActinstType(actType);
        ruActinst.setStatus(ActinstStatusEnum.PENDING.getCode());
        ruActinst.setFlags(0);
        ruActinst.setVarJson(JSON.toJSONString(childShape));
        return ruActinst;
    }

    /**
     * 执行前初始化
     */
    public static void initBeforeExe(RuTaskUser ruTaskUser, CompleteTaskRequest request, String from) {
        CommandContextFactory.getCommandContext().setVarJson(request.getVarJson());
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), from);
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(ruTaskUser.getTaskId());
        CommandContextFactory.getCommandContext().setRuTask(ruTask);
        RuActinst ruActinst = RunTimeManager.checkAndGetRuActinst(ruTask.getActinstId());
        CommandContextFactory.getCommandContext().setRuActinst(ruActinst);
    }

    private static boolean runSequenceFlowAndReturnPassRes(
            Long procinstId,
            String procinstVarJson,
            Long sequenceFlowId) {
        signActStatusFinished(sequenceFlowId);
        RuProcinst ruProcinst = CommandContextFactory.getCommandContext().getRuProcinst();
        Assignee starter = getStarter(ruProcinst);
        return pass(sequenceFlowId, procinstId, procinstVarJson, starter,
                CommandContextFactory.getCommandContext().getOperator());
    }

    private static Assignee getStarter(RuProcinst ruProcinst) {
        Assignee starter = new Assignee();
        starter.setId(ruProcinst.getStarterId());
        starter.setName(ruProcinst.getStarterName());
        starter.setDeptId(ruProcinst.getStarterDeptId());
        starter.setDeptName(ruProcinst.getStarterDeptName());
        starter.setOrgId(ruProcinst.getStarterOrgId());
        starter.setOrgName(ruProcinst.getStarterOrgName());
        return starter;
    }
}
