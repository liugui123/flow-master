package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.enumerate.ProcInstStatusEnum;
import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.model.request.RefuseTaskRequest;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.service.manager.RunTimeManager;

public class RefuseTaskCmd extends PressUserTaskCmd<Void> {

    private final RefuseTaskRequest request;

    public RefuseTaskCmd(RefuseTaskRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public Void execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(ruTaskUser.getProcinstId(), "RefuseTaskCmd");
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        RunTimeManager.checkOperator(ruTaskUser.getAssigneeId(), ruTaskUser.getAssigneeOrgId(), operator);
        CommandContextFactory.getCommandContext().setRuTaskUser(ruTaskUser);
        Long taskId = ruTaskUser.getTaskId();
        RuTask ruTask = RunTimeManager.checkAndGetRuTask(taskId);
        RunTimeManager.delUserTaskAndSendCopyTask(
                ruTaskUser,
                request.getComment(),
                UserTaskStatusEnum.REFUSE);

        RunTimeManager.deleteProcInst(
                ruTask.getProcinstId(),
                ProcInstStatusEnum.REFUSE,
                UserTaskStatusEnum.AUTO_COMPLETE
        );
        return null;
    }


}
