/**
 * 完成分发任务命令
 *
 * @author liugui
 * @date 2021/04/28 22:07
 * @Email liug@shinemo.com
 **/
package org.lg.engine.core.cmd.impl;


import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.FinishTaskRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.model.RuTaskUser;
import org.lg.engine.core.listener.event.WfEngineEventType;
import org.lg.engine.core.listener.event.WfEventBuilder;
import org.lg.engine.core.service.manager.RunTimeManager;

public class FinishReadTaskCmd extends NeedsActiveTaskCmd<Void> {
    private final FinishTaskRequest request;

    public FinishReadTaskCmd(FinishTaskRequest request) {
        super(
                request.getOperator(),
                request.getUserTaskKey(),
                request.getUserTaskLevel(),
                request.getVarJson()
        );
        this.request = request;
    }

    @Override
    protected Void execute(CommandApplication commandApplication, RuTaskUser ruTaskUser) {
        RunTimeManager.initBeforeExe(ruTaskUser, request, "FinishTaskCmd");

        RuTask ruTask = CommandContextFactory.getCommandContext().getRuTask();

        RunTimeManager.delUserTask(
                ruTaskUser,
                request.getComment(),
                UserTaskStatusEnum.READ_FINISHED
        );

        Long actinstIdById = CommandApplications.getRuTaskService().selectFirstActinstIdById(ruTaskUser.getTaskId());
        Assert.isTrue(actinstIdById != null,
                WfExceptionCode.TASK_ACT_ID_BLANK.getMsg(),
                WfExceptionCode.TASK_ACT_ID_BLANK.getCode()
        );

        boolean enableFinish =
                RunTimeManager.enableFinishSubTaskAndDelRemainSubUserTask(ruTaskUser) &&
                        RunTimeManager.enableFinishTaskAndDelRemainUserTask(ruTask.getId(), ruTaskUser.getFlags());
        if (enableFinish) {
            RunTimeManager.delTask(ruTask, WfConstant.DEL_TASK);
            RunTimeManager.signActStatusFinished(ruTask.getActinstId());
            CommandApplications.getEventDispatcher()
                    .dispatchEvent(
                            WfEventBuilder.createEvent(
                                    WfEngineEventType.AUTO_ACT_COMPLETED,
                                    ruTask.getProcinstId(),
                                    ruTask.getId(),
                                    ruTask.getActinstId()
                            )
                    );
        }
        RunTimeManager.autoLeaveAct(ruTaskUser.getProcinstId(), actinstIdById, null, null);

        return null;
    }
}
