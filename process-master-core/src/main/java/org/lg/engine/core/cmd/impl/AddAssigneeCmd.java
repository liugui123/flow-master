package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.ChildShapeAssigneeInfo;
import org.lg.engine.core.client.model.request.AddAssigneeRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.lg.engine.core.service.manager.RunTimeManager;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AddAssigneeCmd extends PressUserTaskCmd<Void> {
    private final AddAssigneeRequest request;

    public AddAssigneeCmd(AddAssigneeRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public Void execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        RunTimeManager.checkSubUserTask(ruTaskUser.getId());
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        Long taskId = ruTaskUser.getTaskId();
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(taskId);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), "AddAssigneeCmd");
        CommandContextFactory.getCommandContext().setRuTask(ruTask);
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        Collection<Assignee> assignees = request.getAssignees();
        Set<String> assigneeIds = CommandApplications.getTaskUserService().selectAssigneeIdByTaskId(taskId);
        assignees = assignees.stream().filter(a -> !assigneeIds.contains(a.getId())).collect(Collectors.toSet());
        RunTimeManager.createUserTaskAndReturnFirst(assignees, WfConstant.ADD_ASSIGNEE,
                taskId, ruTaskUser.getFlags(), true, true);
        return null;
    }


    public static Collection<Assignee> checkAndGetAssignees(ChildShapeAssigneeInfo assigneeInfo) {
        Collection<Assignee> assignees = ProcConfHelper.getAssigneesByChildShapeAssigneeInfo(null, assigneeInfo);
        Assert.isTrue(Utils.isNotEmpty(assignees),
                WfExceptionCode.ADD_ASSIGNEE_BLANK.getMsg(),
                WfExceptionCode.ADD_ASSIGNEE_BLANK.getCode(),
                WfExceptionCode.ADD_ASSIGNEE_BLANK.getMsg()
        );
        return assignees;
    }
}
