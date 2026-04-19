package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.service.manager.RunTimeManager;

public abstract class NeedsActiveTaskCmd<T> extends PressUserTaskCmd<T> {

    protected String userTaskKey;

    public NeedsActiveTaskCmd(Operator user, String userTaskKey, Integer userTaskLevel, String varJson) {
        super(user, userTaskLevel, varJson);
        this.userTaskKey = userTaskKey;
    }

    @Override
    public T execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(userTaskKey);
        RuTaskUser ruTaskUser = CommandApplications.getTaskUserService().checkAndGetUserTaskFromAll(userTaskId);
//        RuTaskUser ruTaskUser = RunTimeManager.checkAndGetRuTaskUser(userTaskId);
        RunTimeManager.checkOperator(ruTaskUser.getAssigneeId(), ruTaskUser.getAssigneeOrgId(), operator);
        RunTimeManager.checkSubUserTask(ruTaskUser.getId());

        return execute(commandApplication, ruTaskUser);
    }


    protected abstract T execute(CommandApplication commandApplication, RuTaskUser ruTaskUser);
}
