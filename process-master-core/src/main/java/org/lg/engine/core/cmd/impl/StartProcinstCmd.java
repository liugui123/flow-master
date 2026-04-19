package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.enumerate.ProcInstFlagsEnum;
import org.lg.engine.core.client.enumerate.ProcInstStatusEnum;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.ChildShapes;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.ReFormModel;
import org.lg.engine.core.db.model.ReProcess;
import org.lg.engine.core.db.model.RuActinst;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.listener.event.WfEngineEventType;
import org.lg.engine.core.listener.event.WfEventBuilder;
import org.lg.engine.core.service.manager.KeyHelper;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.lg.engine.core.service.manager.RunTimeManager;
import org.lg.engine.core.utils.WfUtils;

import java.util.List;

public class StartProcinstCmd extends PressUserTaskCmd<StartProcessResponse> {

    private final StartProcRequest request;

    public StartProcinstCmd(StartProcRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public StartProcessResponse execute(CommandApplication commandApplication) {

        StartProcessResponse res = new StartProcessResponse();
        //流程信息
        ReProcess reProcess = RunTimeManager.checkAndGetReProcess(request.getProcKey(), "StartProcinstCmd:");
        ReFormModel reFormModelById = RunTimeManager.getReFormModelById(reProcess.getFormModelReId());
        CommandContextFactory.getCommandContext().setReFormModel(reFormModelById);
        //流程中的节点配置
        ChildShapes childShapes = ProcConfHelper.checkAndGetChildShapes(reProcess.getProcJson());
        CommandContextFactory.getCommandContext().setReProcess(reProcess);
        //产生流程实例
        RuProcinst ruProcinst = createProcinst(reProcess, request.getVarJson());
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        //运行第一个节点，生成实例和位点信息
        List<RuActinst> ruActinsts = RunTimeManager.initActinstsAndBack(
                childShapes.getChildShapes(),
                null);
        RuActinst startAct = RunTimeManager.getStartRuActinst(ruActinsts);
        //加入流程相关信息，后面用到不用查了
        CommandContextFactory.getCommandContext().setRuActinst(startAct);


        String taskLevel = WfUtils.getJsonVal(ruProcinst.getVarJson(), "_taskLevel");

        if (Utils.isNumeric(taskLevel)) {
            CommandContextFactory.getCommandContext().setUserTaskLevel(Integer.valueOf(taskLevel));
        }
        if (request.getRunFirstElement()) {
            if (request instanceof AutoStartProcRequest) {
                String aLong = RunTimeManager.runFirstActinst(
                        startAct.getId(),
                        operator,
                        null,
                        request.getVarJson(),
                        request.getComment());
                res.setUserTaskKey(aLong);
            } else if (request instanceof ManualStartProcRequest) {
                List<ManualStartInfo> manualStartInfos = ((ManualStartProcRequest) request).getManualStartInfo();
                Assert.isTrue(
                        Utils.isNotEmpty(manualStartInfos),
                        WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getMsg(),
                        WfExceptionCode.MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK.getCode()
                );
                String aLong = RunTimeManager.runFirstActinst(
                        startAct.getId(),
                        operator,
                        manualStartInfos,
                        request.getVarJson(),
                        request.getComment());
                res.setUserTaskKey(aLong);
            }
        }
        res.setProcinstKey(ruProcinst.getProcKey());
        res.setStartActinstKey(startAct.getActinstKey());
        //发布流程启动事件
        CommandApplications.getEventDispatcher()
                .dispatchEvent(
                        WfEventBuilder.createEvent(
                                WfEngineEventType.PROCINST_STARTED,
                                ruProcinst.getId(),
                                null,
                                null
                        )
                );
        return res;
    }

    public RuProcinst createProcinst(ReProcess reProcess, String varJson) {
        RuProcinst ruProcinst = new RuProcinst();
        ruProcinst.setStatus(ProcInstStatusEnum.RUNNING.getStatus());
        ruProcinst.setFlags(ProcInstFlagsEnum.DEFAULT.getFlags());
        ruProcinst.setGmtCreate(System.currentTimeMillis());
        ruProcinst.setGmtModified(System.currentTimeMillis());
        ruProcinst.setProcName(reProcess.getProcName());
        ruProcinst.setProcKey(KeyHelper.randomKey(WfConstant.WF_PROC_INST));
        ruProcinst.setProcDeKey(reProcess.getProcKey());
        ruProcinst.setStarterId(operator.getId());
        ruProcinst.setStarterName(operator.getName());
        ruProcinst.setStarterDeptId(operator.getDeptId());
        ruProcinst.setStarterDeptName(operator.getDeptName());
        ruProcinst.setStarterOrgId(operator.getOrgId());
        ruProcinst.setStarterOrgName(operator.getOrgName());
        ruProcinst.setVarJson(varJson);
        ruProcinst.setTenant("wf");
        ruProcinst.setPid(request.getPid());
        ruProcinst.setProcFormDataKey(request.getProcFormDataKey());
        //获取表单模型信息，填充
        ReFormModel reFormModel = CommandContextFactory.getCommandContext().getReFormModel();
        ruProcinst.setProcFormKey(reFormModel.getFormModelKey());
        ruProcinst.setBizId(reFormModel.getBizId());
        ruProcinst.setProcReId(reProcess.getId());
        ruProcinst.setProcFormUrl(reFormModel.getFormModelUrl());
        ruProcinst.setProcFormUrlApp(reFormModel.getFormModelUrlApp());
        ruProcinst.setProcFormStartUrlApp(reFormModel.getFormModelStartUrlApp());
        ruProcinst.setTaskTitle(reFormModel.getTaskTitle());
        ruProcinst.setTaskType(reFormModel.getTaskType());
        ruProcinst.setAppKey(reFormModel.getAppKey());

        ruProcinst.setProcViewJson(reProcess.getProcViewJson());
        ruProcinst.setProcJson(reProcess.getProcJson());
        CommandApplications.getProcinstService().insert(ruProcinst);
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);
        return ruProcinst;
    }


}
