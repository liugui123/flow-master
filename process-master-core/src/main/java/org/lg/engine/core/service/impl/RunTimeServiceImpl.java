package org.lg.engine.core.service.impl;

import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.CompleteTaskResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.core.cmd.impl.*;
import org.lg.engine.core.service.AbstractService;
import org.lg.engine.core.service.RunTimeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class RunTimeServiceImpl extends AbstractService implements RunTimeService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStartDraft(SaveStartDraftRequest request) {
        commandExecutor.execute(new SaveStartDraftCmd(request));
    }

    @Override
    public void delStartDraft(DelStartDraftRequest request) {
        commandExecutor.execute(new DelStartDraftCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StartProcessResponse startProcess(StartProcRequest request) {
        return commandExecutor.execute(new StartProcinstCmd(request));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteTaskResponse completeTask(CompleteTaskRequest request) {
        return commandExecutor.execute(new CompleteTaskCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revoke(RevokeTaskRequest request) {
        commandExecutor.execute(new RevokeProcessCmd(request));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void back(ManualBackRequest request) {
        commandExecutor.execute(new BackCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuse(RefuseTaskRequest request) {
        commandExecutor.execute(new RefuseTaskCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(SignRequest request) {
        commandExecutor.execute(new SignCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userTaskDeliver(UserTaskDeliverRequest request) {
        commandExecutor.execute(new UserTaskDeliverCmd(request));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taskAddAssignee(AddAssigneeRequest request) {
        commandExecutor.execute(new AddAssigneeCmd(request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishTask(FinishTaskRequest request) {
        commandExecutor.execute(new FinishReadTaskCmd(request));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ruProcinstUpdate(RuprocinstUpdateRequest request) {
        commandExecutor.execute(new RuProcinstUpdateCmd(request));
    }
}
