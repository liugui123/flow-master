package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.lg.engine.core.aspect.RepeatSubmit;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.AutoCompleteTaskResponse;
import org.lg.engine.core.client.model.response.ManualCompleteTaskResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.core.client.service.WfExeService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.service.RunTimeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service
@Component
public class WfExeServiceProvider implements WfExeService {

    @Resource
    RunTimeService runTimeService;

    @RepeatSubmit
    @Override
    public ApiResult<Void> saveStartDraft(SaveStartDraftRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.saveStartDraft(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> delStartDraft(DelStartDraftRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.delStartDraft(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<StartProcessResponse> autoStartProcess(@Valid AutoStartProcRequest request) {
        Utils.validateEntity(request, Default.class);
        StartProcessResponse startProcessResponse = runTimeService.startProcess(request);
        return ApiResult.success(startProcessResponse);
    }

    @Override
    public ApiResult<StartProcessResponse> manualStartProcess(@Valid ManualStartProcRequest request) {
        Utils.validateEntity(request, Default.class);
        StartProcessResponse startProcessResponse = runTimeService.startProcess(request);
        return ApiResult.success(startProcessResponse);
    }

    /**
     * 自动完成个人待办
     *
     * @param request
     * @return
     */
    @RepeatSubmit
    @Override
    public ApiResult<AutoCompleteTaskResponse> autoCompleteTask(@Valid AutoCompleteTaskRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.completeTask(request);
        return ApiResult.success();
    }

    /**
     * 手动完成个人待办
     *
     * @param request
     * @return
     */
    @RepeatSubmit
    @Override
    public ApiResult<ManualCompleteTaskResponse> manualCompleteTask(@Valid ManualCompleteTaskRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.completeTask(request);
        return ApiResult.success();
    }


    @RepeatSubmit
    @Override
    public ApiResult<Void> revoke(@Valid RevokeTaskRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.revoke(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> refuse(RefuseTaskRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.refuse(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> sign(@Valid SignRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.sign(request);
        return ApiResult.success();
    }


    @RepeatSubmit
    @Override
    public ApiResult<Void> userTaskDeliver(@Valid UserTaskDeliverRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.userTaskDeliver(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> addAssignee(@Valid AddAssigneeRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.taskAddAssignee(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> finishTask(FinishTaskRequest request) {
        runTimeService.finishTask(request);
        return ApiResult.success();
    }

    @RepeatSubmit
    @Override
    public ApiResult<Void> manualBack(ManualBackRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.back(request);
        return ApiResult.success();
    }

}
