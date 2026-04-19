package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.model.request.SaveStartDraftRequest;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.service.manager.RunTimeManager;

/**
 * 保存启动草稿
 */
public class SaveStartDraftCmd extends PressUserTaskCmd<Void> {

    private final SaveStartDraftRequest request;

    public SaveStartDraftCmd(SaveStartDraftRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }

    @Override
    public Void execute(CommandApplication commandApplication) {
        RuStartDraft ruStartDraft = Convert.INSTANCE.editReqToRuStartDraft(request);
        //添加应用key
        String procKey = request.getProcKey();
        DeProcess deProcess = RunTimeManager.checkAndGetDeProcess(procKey, "保存启动草稿：");
        ReFormModel reFormModel = RunTimeManager.checkAndGetReFormModel(deProcess.getFormModelReId(), "保存启动草稿：");

        String draftTitle = request.getDraftTitle();
        if (Utils.isEmpty(draftTitle)) {
            //草稿标题默认使用表单的名字
            ruStartDraft.setDraftTitle(reFormModel.getTaskTitle());
        } else {
            ruStartDraft.setDraftTitle(draftTitle);
        }

        ruStartDraft.setFormModelStartUrl(reFormModel.getFormModelStartUrl());
        ruStartDraft.setFormModelStartUrlApp(reFormModel.getFormModelStartUrlApp());

        ruStartDraft.setProcKey(deProcess.getProcKey());
        ruStartDraft.setProcFormKey(reFormModel.getFormModelKey());

        ruStartDraft.setAppKey(deProcess.getAppKey());

        ruStartDraft.setAssigneeId(operator.getId());
        ruStartDraft.setAssigneeName(operator.getName());

        ruStartDraft.setAssigneeDeptId(operator.getDeptId());
        ruStartDraft.setAssigneeDeptName(operator.getDeptName());
        ruStartDraft.setAssigneeOrgId(operator.getOrgId());
        ruStartDraft.setAssigneeOrgName(operator.getOrgName());
        //保存草稿
        CommandApplications.getRuStartDraftService().edit(ruStartDraft);
        return null;
    }
}
