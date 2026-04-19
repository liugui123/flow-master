package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.client.model.request.DelStartDraftRequest;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;

/**
 * 保存启动草稿
 */
public class DelStartDraftCmd extends PressUserTaskCmd<Void> {

    private final DelStartDraftRequest request;

    public DelStartDraftCmd(DelStartDraftRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }


    @Override
    public Void execute(CommandApplication commandApplication) {

        String procFormDataKey = request.getProcFormDataKey();

        CommandApplications.getRuStartDraftService().deleteByProcFormDataKey(procFormDataKey);
        return null;
    }
}
