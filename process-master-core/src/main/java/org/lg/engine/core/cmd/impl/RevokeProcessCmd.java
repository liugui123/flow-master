package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.enumerate.ProcInstStatusEnum;
import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.RevokeTaskRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.HiRuTaskUser;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.service.manager.RunTimeManager;

/**
 * 撤销流程
 */
public class RevokeProcessCmd extends PressUserTaskCmd<Void> {

    private final RevokeTaskRequest request;

    public RevokeProcessCmd(RevokeTaskRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public Void execute(CommandApplication commandApplication) {
        Long userTaskId = RunTimeManager.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        HiRuTaskUser hiRuTaskUser = RunTimeManager.checkAndGetHiRuTaskUser(userTaskId);
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinst(hiRuTaskUser.getProcinstId(), "RevokeProcessCmd");
        Assert.isTrue(ProcInstStatusEnum.RUNNING.getStatus().equals(ruProcinst.getStatus())
                , WfExceptionCode.REVOKE_ERROR_PROCINST_NOT_RUNNING.getMsg(),
                WfExceptionCode.REVOKE_ERROR_PROCINST_NOT_RUNNING.getCode());
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        RunTimeManager.checkOperator(hiRuTaskUser.getAssigneeId(), hiRuTaskUser.getAssigneeOrgId(), operator);

//        HiRuTask hiRuTask = RunTimeManager.checkAndGetHiRuTask(hiRuTaskUser.getTaskId());
//        Map<Long, Long> sequenceIdAndNextFlowActIdRel = RunTimeManager.getSequenceIdAndNextFlowActIdRel(hiRuTask.getActinstId());
//        Collection<Long> targetAct = sequenceIdAndNextFlowActIdRel.values();
//        List<Long> actIds = CommandApplications.getActinstService().selectIdByIdInAndStatus(targetAct, ActinstStatusEnum.FINISHED.getCode());
//        //todo 这里节点已经处理了应该也可以撤销流程的,先住掉
//        Assert.isTrue(Utils.isEmpty(actIds),
//                WfExceptionCode.REVOKE_NOT_SUP_ACTINST_DONE.getMsg(),
//                WfExceptionCode.REVOKE_NOT_SUP_ACTINST_DONE.getCode()
//        );

        RunTimeManager.deleteProcInst(
//                hiRuTask.getProcinstId(),
                ruProcinst.getId(),
                ProcInstStatusEnum.REVOKE,
                UserTaskStatusEnum.REVOKE
        );
        return null;
    }
}
