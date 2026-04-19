package org.lg.engine.core.cmd.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.cmd.AbstractCommand;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.db.model.DeProcess;
import org.lg.engine.core.db.model.ReProcess;

/**
 * 发布流程
 */
public class PublishProcessDefCmd extends AbstractCommand<Boolean> {

    private final String defProcessKey;
    private final Long formModelReId;


    public PublishProcessDefCmd(Operator user, String defProcessKey, Long formModelReId) {
        super(user);
        this.defProcessKey = defProcessKey;
        this.formModelReId = formModelReId;
    }

    @Override
    public Boolean execute(CommandApplication commandApplication) {
        DeProcess deProcess = CommandApplications.getDeProcessService().selectFirstByProcKey(defProcessKey);
        deProcess.setFormModelReId(formModelReId);
        return insertReProcess(deProcess);
    }


    private Boolean insertReProcess(DeProcess deProcess) {
        ReProcess reProcess = Convert.INSTANCE.deProcessToReProcess(deProcess);
        ReProcess reProcesse = CommandApplications.getReProcessService().selectFirstByProcKeyOrderByProcVersionDesc(deProcess.getProcKey());
        if (reProcesse != null) {
            reProcess.setProcVersion(reProcesse.getProcVersion() + 1);
        } else {
            reProcess.setProcVersion(0);
        }
        reProcess.setProcDeId(deProcess.getId());
        CommandApplications.getReProcessService().insert(reProcess);
        return true;
    }

}
