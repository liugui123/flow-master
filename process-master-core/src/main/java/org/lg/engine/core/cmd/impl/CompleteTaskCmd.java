package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.CompleteTaskRequest;
import org.lg.engine.core.client.model.request.ManualCompleteTaskRequest;
import org.lg.engine.core.client.model.response.CompleteTaskResponse;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.service.manager.RunTimeManager;

public class CompleteTaskCmd extends NeedsActiveTaskCmd<CompleteTaskResponse> {

    private final CompleteTaskRequest request;

    public CompleteTaskCmd(CompleteTaskRequest request) {
        super(request.getOperator(), request.getUserTaskKey(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    protected CompleteTaskResponse execute(CommandApplication commandApplication, RuTaskUser ruTaskUser) {
        RunTimeManager.initBeforeExe(ruTaskUser, request, "CompleteTaskCmd");

        CommandContextFactory.getCommandContext().setOperator(request.getOperator());
        // 用户等级 用于特急 一般 的待办提醒 !!
        CommandContextFactory.getCommandContext().setUserTaskLevel(request.getUserTaskLevel());

        // 手动完成个人待办逻辑 >> 选节点 >> 手动处理传递的节点和处理人信息
        if (request instanceof ManualCompleteTaskRequest) {
            ManualCompleteTaskRequest manualCompleteTaskRequest = (ManualCompleteTaskRequest) request;
            Assert.isTrue(
                    Utils.isNotEmpty(manualCompleteTaskRequest.getManualCompleteInfos()),
                    WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getMsg(),
                    WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getCode()
            );

            // 手动完成用户任务 >> 选节点进入
            return RunTimeManager.manualFinishTaskAndLeave(ruTaskUser,
                    request.getOptionDesc(), request.getComment(),
                    manualCompleteTaskRequest.getManualCompleteInfos(),
                    manualCompleteTaskRequest.getVarJson()
            );
        } else {
            //自动完成逻辑
            return RunTimeManager.autoFinishTaskAndLeave(false, ruTaskUser,
                    request.getOptionDesc(), request.getComment(), request.getVarJson(),
                    null);
        }
    }
}
