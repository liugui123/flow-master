package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.SignFlagsEnum;
import org.lg.engine.core.client.enumerate.UserTaskFlagsEnum;
import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.WfUser;
import org.lg.engine.core.client.model.request.SignRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.service.manager.RunTimeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SignCmd extends PressUserTaskCmd<Void> {

    private final SignRequest request;

    public SignCmd(SignRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }


    @Override
    public Void execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        RunTimeManager.checkOperator(ruTaskUser.getAssigneeId(), ruTaskUser.getAssigneeOrgId(), operator);
        RunTimeManager.checkSubUserTask(ruTaskUser.getId());
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), "SignCmd");
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(ruTaskUser.getTaskId());
        CommandContextFactory.getCommandContext().setRuTask(ruTask);
        Integer flags = request.getFlags();
        Assert.isTrue(!SignFlagsEnum.BEFORE_ME.getFlag().equals(flags) || !SignFlagsEnum.AFTER_ME.getFlag().equals(flags)
                , WfExceptionCode.SIGN_TYPE_ERROR.getMsg(),
                WfExceptionCode.SIGN_TYPE_ERROR.getCode());
        List<RuTaskUser> signTaskUsers = initSignTaskUsers(ruTaskUser, request.getAssignees());
        RunTimeManager.delUserTask(
                ruTaskUser,
                request.getComment(),
                UserTaskStatusEnum.AUTO_COMPLETE);
        if (SignFlagsEnum.BEFORE_ME.getFlag().equals(flags)) {
            for (RuTaskUser signTaskUser : signTaskUsers) {
                signTaskUser.setFlags(UserTaskFlagsEnum.COUNTERSIGN.getFlag() + UserTaskFlagsEnum.BEFORE_SIGN.getFlag());
            }
        } else if (SignFlagsEnum.AFTER_ME.getFlag().equals(flags)) {
            for (RuTaskUser signTaskUser : signTaskUsers) {
                signTaskUser.setFlags(UserTaskFlagsEnum.COUNTERSIGN.getFlag() + UserTaskFlagsEnum.AFTER_SIGN.getFlag());
            }
        }
        RunTimeManager.checkAgentAndSaveUserTasks(UserTaskFlagsEnum.COUNTERSIGN.getFlag(), true, signTaskUsers, true);
        return null;
    }


    private List<RuTaskUser> initSignTaskUsers(RuTaskUser ruTaskUser, Collection<Assignee> assignees) {
        List<RuTaskUser> ruTaskUsers = new ArrayList<>(assignees.size());
        //代理人存在，保存原任务人员信息
//        List<TaskUserAgentTask> agentTasks = new ArrayList<>(1024);
        for (Assignee assignee : assignees) {
            RuTaskUser signTaskUser = Convert.INSTANCE.ruTaskUserToRuTaskUser(ruTaskUser);
            signTaskUser.setUserTaskKey(UUID.randomUUID().toString().replace("-", ""));
            signTaskUser.setTaskId(ruTaskUser.getTaskId());
            signTaskUser.setGmtModified(System.currentTimeMillis());
            signTaskUser.setGmtCreate(System.currentTimeMillis());
            signTaskUser.setBizId(ruTaskUser.getBizId());
            signTaskUser.setPid(ruTaskUser.getId());
            signTaskUser.setAssigneeId(assignee.getId());
            signTaskUser.setAssigneeName(assignee.getName());

            signTaskUser.setAssigneeDeptId(assignee.getDeptId());
            signTaskUser.setAssigneeDeptName(assignee.getDeptName());
            signTaskUser.setAssigneeOrgId(assignee.getOrgId());
            signTaskUser.setAssigneeOrgName(assignee.getOrgName());

            signTaskUser.setGmtModified(System.currentTimeMillis());
            signTaskUser.setGmtCreate(System.currentTimeMillis());

            Integer taskUserFlags = ruTaskUser.getFlags();
            if (UserTaskFlagsEnum.BEFORE_SIGN.hasTag(ruTaskUser.getFlags())) {
                taskUserFlags -= UserTaskFlagsEnum.BEFORE_SIGN.getFlag();
            } else if (UserTaskFlagsEnum.AFTER_SIGN.hasTag(ruTaskUser.getFlags())) {
                taskUserFlags -= UserTaskFlagsEnum.AFTER_SIGN.getFlag();
            }
            signTaskUser.setFlags(taskUserFlags);
            ruTaskUsers.add(signTaskUser);

//            if (assignee.getReplace() != null) {
//                WfUser replace = assignee.getReplace();
//                TaskUserAgentTask agentTask = new TaskUserAgentTask();
//                agentTask.setAgentTaskUserKey(signTaskUser.getUserTaskKey());
//                agentTask.setAssigneeId(replace.getId());
//                agentTask.setAssigneeName(replace.getName());
//                agentTask.setAssigneeDeptId(replace.getDeptId());
//                agentTask.setAssigneeDeptName(replace.getDeptName());
//                agentTask.setAssigneeOrgId(replace.getOrgId());
//                agentTask.setAssigneeOrgName(replace.getOrgName());
//                agentTasks.add(agentTask);
//            }
        }
//        CommandApplications.getTaskUserAgentTaskService().batchInsert(agentTasks);
        return ruTaskUsers;
    }


}
