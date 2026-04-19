package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.request.UserTaskDeliverRequest;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.service.manager.RunTimeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserTaskDeliverCmd extends PressUserTaskCmd<Void> {
    private final UserTaskDeliverRequest request;

    public UserTaskDeliverCmd(UserTaskDeliverRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public Void execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(ruTaskUser.getTaskId());
        CommandContextFactory.getCommandContext().setRuTask(ruTask);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), "UserTaskDeliverCmd");
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        RunTimeManager.checkOperator(ruTaskUser.getAssigneeId(), ruTaskUser.getAssigneeOrgId(), operator);
        RunTimeManager.checkSubUserTask(ruTaskUser.getId());
        RunTimeManager.delUserTask(
                ruTaskUser,
                request.getComment(),
                UserTaskStatusEnum.DELIVERED);
        Assignee assignee = request.getAssignee();
        //更新处理人信息
        ruTaskUser.setId(null);
        ruTaskUser.setUserTaskKey(UUID.randomUUID().toString().replace("-", ""));
        ruTaskUser.setAssigneeId(assignee.getId());
        ruTaskUser.setAssigneeName(assignee.getName());
        ruTaskUser.setAssigneeDeptId(assignee.getDeptId());
        ruTaskUser.setAssigneeDeptName(assignee.getDeptName());
        ruTaskUser.setAssigneeOrgId(assignee.getOrgId());
        ruTaskUser.setAssigneeOrgName(assignee.getOrgName());

        List<RuTaskUser> ruTaskUsers = new ArrayList<>(1);
        ruTaskUsers.add(ruTaskUser);
        RunTimeManager.checkAgentAndSaveUserTasks(ruTaskUser.getFlags(), true, ruTaskUsers, true);
        return null;
    }


}
