package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.enumerate.ActinstStatusEnum;
import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.request.ManualBackRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.listener.event.WfEngineEventType;
import org.lg.engine.core.listener.event.WfEventBuilder;
import org.lg.engine.core.service.manager.RunTimeManager;

import java.util.*;

public class BackCmd extends PressUserTaskCmd<Void> {

    private final ManualBackRequest request;

    public BackCmd(ManualBackRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }


    @Override
    public Void execute(CommandApplication commandApplication) {
        //做一些初始化
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        CommandContextFactory.getCommandContext().setOperator(request.getOperator());
        RunTimeManager.checkOperator(ruTaskUser.getAssigneeId(), ruTaskUser.getAssigneeOrgId(), operator);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), "BackCmd");
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);

        //拿到节点配置
        Long taskId = ruTaskUser.getTaskId();
        RunTimeManager.checkSubUserTask(ruTaskUser.getId());
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(taskId);


        Long backActinstId = request.getActinstId();
        Long backTaskId = checkActFinished(backActinstId);
        //拿到节点配置后
//        RuActinst ruActinst = RunTimeManager.checkAndGetRuActinst(ruTask.getActinstId());

//        List<Long> actinstIds = new ArrayList<>();
//        if (request instanceof ManualBackRequest) {
//            Long actinstId = ((ManualBackRequest) request).getActinstId();
//            actinstIds.add(actinstId);
//        } else {
//            // 自动模式 查此节点的回退的节点配置 1.没配置回退节点原逻辑 2.配置了回退节点或多个 => 取第一个
//            // ps 后端无法判断 回退按钮是否打开了 >> 目前直接判断 backAbleActKeys 是否配置 不严谨 >> 是否存在未开启回退 backAbleActKeys 不为空的情况？
//            ChildShape childShapeByActJson = ProcConfHelper.getChildShapeByActJson(varJson);
//            List<String> backAbleActKeys = childShapeByActJson.getBackAbleActKeys();
//            if (CollectionUtils.isNotEmpty(backAbleActKeys)) {
//                Long actinstId = RunTimeManager.getActinstIdByActKey(backAbleActKeys.get(0));
//                actinstIds.add(actinstId);
//            } else {
////            ProcConfHelper.getBackActinstIds(Sets.newHashSet(ruTask.getActinstId()), outActinstIdSet, 1);
//                List<Long> sequenceIds = RunTimeManager.selectsFinishedActinstIdsByByTatgetId(ruTask.getActinstId());
//                if (Utils.isNotEmpty(sequenceIds)) {
//                    actinstIds.addAll(RunTimeManager.selectsFinishedActinstIdsByByTatgetId(sequenceIds.get(0)));
//                }
//            }
//        }
//        actinstIds.forEach(v -> checkActFinished(v));
        //标记当前用户任务退回处理
        RunTimeManager.delUserTask(ruTaskUser, request.getComment(), UserTaskStatusEnum.BACK);
        //退回后需要恢复的节点
        Set<Long> recoverActinst = recoverActinst(backActinstId, ruTask.getProcinstId());
        //当前的任务也要删除
        recoverActinst.add(ruTask.getId());
        //其他没有完成的任务标记自动完成
        delTaskAndUserTasksByActinstIdIn(recoverActinst, UserTaskStatusEnum.AUTO_COMPLETE);

        Collection<Assignee> assignees = new ArrayList<>();
        if (backTaskId != null) {
            //找到退回节点历史处理人
            assignees = getBackActAssignees(backTaskId);
            Assert.isTrue(Utils.isNotEmpty(assignees),
                    WfExceptionCode.BACK_ACT_HI_USER_EMPTY.getMsg(),
                    WfExceptionCode.BACK_ACT_HI_USER_EMPTY.getCode());
        }

        //重新执行退回节点
        //退回的节点的审批类型配置
//        int userTaskFlagsByActinst = ProcConfHelper.findUserTaskFlagsByVarJson(varJson);
        RunTimeManager.runActinstAndSignEnd(
                ruTask.getProcinstId(),
                backActinstId,
                assignees);

        //触发节点退回事件
        CommandApplications.getEventDispatcher().dispatchEvent(
                WfEventBuilder.createEvent(
                        WfEngineEventType.ACT_BACK,
                        ruTask.getProcinstId(),
                        taskId,
                        ruTask.getActinstId()
                )
        );
//        List<HiRuTaskUser> reqHiRuTaskUsers = CommandApplications.getHiRuTaskUserService()
//                .selectByTaskUserKey(request.getUserTaskKey());
//        if (CollectionUtils.isNotEmpty(reqHiRuTaskUsers)) {
//            for (HiRuTaskUser hiRuTaskUser : reqHiRuTaskUsers) {
//                hiRuTaskUser.setComment(request.getComment());
//                hiRuTaskUser.setGmtEnd(System.currentTimeMillis());
//                CommandApplications.getHiRuTaskUserService().updateByPrimaryKey(hiRuTaskUser);
//            }
//        }
//        for (Long actinstId : actinstIds) {
//            Long backTaskId = CommandApplications.getHiRuTaskService().selectFirstTaskIdByActinstId(backActinstId);
//            Set<Integer> statusCollection = new HashSet<>();
//            statusCollection.add(UserTaskStatusEnum.COMMON_FINISH.getStatus());
//            statusCollection.add(UserTaskStatusEnum.REVOKE.getStatus());
//            statusCollection.add(UserTaskStatusEnum.REFUSE.getStatus());
//            List<HiRuTaskUser> hiRuTaskUsers = CommandApplications.getHiRuTaskUserService()
//                    .selectByTaskIdAndStatusIn(backTaskId, statusCollection);
//            Collection<Assignee> assignees = new HashSet<>(hiRuTaskUsers.size());
//
//            for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
//                if (hiRuTaskUser == null) {
//                    continue;
//                }
//                Assignee assignee = new Assignee();
//                assignee.setId(hiRuTaskUser.getAssigneeId());
//                assignee.setName(hiRuTaskUser.getAssigneeName());
//
//                assignee.setDeptName(hiRuTaskUser.getAssigneeDeptName());
//                assignee.setDeptId(hiRuTaskUser.getAssigneeDeptId());
//
//                assignee.setOrgId(hiRuTaskUser.getAssigneeOrgId());
//                assignee.setOrgName(hiRuTaskUser.getAssigneeOrgName());
//                if (!assignees.contains(assignee)) {
//                    assignees.add(assignee);
//                }
//            }
//            //退回的节点的审批类型配置
//            int userTaskFlagsByActinst = ProcConfHelper.findUserTaskFlagsByVarJson(varJson);
//
//            if (Utils.isNotEmpty(assignees)) {
//                ChildShapeAssigneeInfo assigneeInfo = new ChildShapeAssigneeInfo();
//                assigneeInfo.setAssignees(assignees);
//                RunTimeManager.runActinstAndSignEnd(ruTask.getProcinstId(), backActinstId, assigneeInfo, UserTaskFlagsEnum.getByFlag(userTaskFlagsByActinst));
//            } else {
//                RunTimeManager.runActinstAndSignEnd(ruTask.getProcinstId(), backActinstId, null, UserTaskFlagsEnum.getByFlag(userTaskFlagsByActinst));
//            }
//            CommandApplications.getEventDispatcher().dispatchEvent(
//                    WfEventBuilder.createEvent(
//                            WfEngineEventType.ACT_BACK,
//                            ruTask.getProcinstId(),
//                            taskId,
//                            backActinstId
//                    )
//            );
//        }

        // 查询当前的节点信息 是否开启了直送功能 >> 开启了 记录日志  ps actinstIds 上面传入的是退回多个节点的？
//        Assert.isTrue(ruActinst != null,
//                WfExceptionCode.ACTINST_BLANK.getMsg(),
//                WfExceptionCode.ACTINST_BLANK.getCode()
//        );
//        ChildShape childShapeByActJson = ProcConfHelper.getChildShapeByActJson(ruActinst.getVarJson());
//        if (childShapeByActJson.getBackOut()) {
//            // 开启了直送配置 记录一下日志
//            FallbackRecord fallbackRecord = new FallbackRecord();
//            fallbackRecord.setProcinstId(ruTaskUser.getProcinstId());
//            // 当前节点任务id
//            fallbackRecord.setTaskId(taskId);
//            // 回退到这个节点
//            fallbackRecord.setFallbackActinstId(actinstIds.get(0));
//            // 上下文取的uid是0 直接用户任务里面取
//            fallbackRecord.setUserId(ruTaskUser.getAssigneeId());
//            fallbackRecord.setGmtCreate(LocalDateTime.now());
//            fallbackRecord.setGmtModified(LocalDateTime.now());
//            CommandApplications.getFallbackRecordService().insert(fallbackRecord);
//        }

        return null;
    }

    /**
     * 获取退回节点的历史处理人
     */
    private static Collection<Assignee> getBackActAssignees(Long backTaskId) {
        Set<Integer> statusCollection = new HashSet<>();
        statusCollection.add(UserTaskStatusEnum.COMMON_FINISH.getStatus());
        statusCollection.add(UserTaskStatusEnum.REVOKE.getStatus());
        statusCollection.add(UserTaskStatusEnum.READ_FINISHED.getStatus());
        statusCollection.add(UserTaskStatusEnum.AUTO_COMPLETE.getStatus());
        statusCollection.add(UserTaskStatusEnum.DELIVERED.getStatus());
        List<HiRuTaskUser> hiRuTaskUsers = CommandApplications.getHiRuTaskUserService()
                .selectByTaskIdAndStatusIn(backTaskId, statusCollection);
        Collection<Assignee> assignees = new HashSet<>(hiRuTaskUsers.size());

        for (HiRuTaskUser hiRuTaskUser : hiRuTaskUsers) {
            if (hiRuTaskUser == null) {
                continue;
            }
            Assignee assignee = new Assignee();
            assignee.setId(hiRuTaskUser.getAssigneeId());
            assignee.setName(hiRuTaskUser.getAssigneeName());

            assignee.setDeptName(hiRuTaskUser.getAssigneeDeptName());
            assignee.setDeptId(hiRuTaskUser.getAssigneeDeptId());

            assignee.setOrgId(hiRuTaskUser.getAssigneeOrgId());
            assignee.setOrgName(hiRuTaskUser.getAssigneeOrgName());
            if (!assignees.contains(assignee)) {
                assignees.add(assignee);
            }
        }
        return assignees;
    }


    private Long checkActFinished(Long actinstId) {
        Integer backActStatus = CommandApplications.getActinstService().selectStatusById(actinstId);
        boolean finished = ActinstStatusEnum.FINISHED.getCode().equals(backActStatus);
        //pd逻辑, 节点下没有进行中任务说明该节点没有参与实际流程的流转
//        if (!finished) {
//            List<RuTask> ruTasks = CommandApplications.getRuTaskService().selectRuTaskByActInstId(actinstId);
//            if (CollectionUtils.isEmpty(ruTasks)) {
//                finished = true;
//            } else {
//                finished = ruTasks.stream().allMatch(v -> RuTaskStatusEnum.DONE.getStatus().equals(v.getStatus()));
//            }
//        }
        Assert.isTrue(finished,
                WfExceptionCode.TASK_DESC_BACK_ACT_NOT_FINISHED.getMsg(),
                WfExceptionCode.TASK_DESC_BACK_ACT_NOT_FINISHED.getCode());

        return CommandApplications.getHiRuTaskService().selectFirstTaskIdByActinstId(actinstId);
    }

    /**
     * 找到actinstIds节点位置后面的actinstid列表，并标记为要恢复的ID
     */
    private Set<Long> recoverActinst(Long backActinstId, Long procinstId) {
//        CommandApplications.getActinstService().updateStatusByIdIn(ActinstStatusEnum.RUNNING.getCode(), actinstIds);
        List<RuActinstSite> ruActinstSites = CommandApplications.getActinstSiteService().selectByProInstId(procinstId);
        Set<Long> siteIds = new HashSet<>();
        //填充回退节点
        initSiteIds(backActinstId, siteIds, ruActinstSites);

        CommandApplications.getActinstService().updateStatusByIdIn(ActinstStatusEnum.PENDING.getCode(), siteIds);
        return siteIds;
    }


    private void initSiteIds(Long actinstId, Set<Long> siteIds, List<RuActinstSite> ruActinstSites) {
        if (siteIds.contains(actinstId)) {
            return;
        }
        siteIds.add(actinstId);
        List<Long> targetActIdByActIds = findTargetActIdByActId(actinstId, ruActinstSites);
        for (Long targetActIdByActId : targetActIdByActIds) {
            initSiteIds(targetActIdByActId, siteIds, ruActinstSites);
        }
    }

    private List<Long> findTargetActIdByActId(Long actinstId, List<RuActinstSite> ruActinstSites) {
        List<Long> res = new ArrayList<>();
        for (RuActinstSite ruActinstSite : ruActinstSites) {
            if (actinstId.equals(ruActinstSite.getActinstId())) {
                res.add(ruActinstSite.getTargetId());
            }
        }
        return res;
    }


    public static void delTaskAndUserTasksByActinstIdIn(Set<Long> actIdsAfterBackAct,
                                                        UserTaskStatusEnum statusEnum) {
        List<RuTask> ruTasks = CommandApplications.getRuTaskService().selectByActinstIdIn(actIdsAfterBackAct);
        if (Utils.isEmpty(ruTasks)) {
            return;
        }
        for (RuTask ruTask : ruTasks) {
            RunTimeManager.delTask(ruTask, statusEnum.getDesc());
            RunTimeManager.delUserTaskByTaskId(ruTask.getId(), statusEnum);
        }
    }
}

