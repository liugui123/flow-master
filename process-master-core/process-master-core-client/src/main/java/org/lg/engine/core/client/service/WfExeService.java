package org.lg.engine.core.client.service;


import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.AutoCompleteTaskResponse;
import org.lg.engine.core.client.model.response.ManualCompleteTaskResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.core.client.utils.ApiResult;


public interface WfExeService {

    /**
     * 保存启动草稿
     */
    ApiResult<Void> saveStartDraft(SaveStartDraftRequest request);

    /**
     * 删除启动草稿
     */
    ApiResult<Void> delStartDraft(DelStartDraftRequest request);

    /**
     * 启动流程
     */
    ApiResult<StartProcessResponse> autoStartProcess(AutoStartProcRequest request);

    /**
     * 手动启动流程
     */
    ApiResult<StartProcessResponse> manualStartProcess( ManualStartProcRequest request);

    /**
     * 自动完成待办
     */
    ApiResult<AutoCompleteTaskResponse> autoCompleteTask(AutoCompleteTaskRequest request);

    /**
     * 手动完成待办
     */
    ApiResult<ManualCompleteTaskResponse> manualCompleteTask(ManualCompleteTaskRequest request);

    /**
     * 撤销
     */
    ApiResult<Void> revoke(RevokeTaskRequest request);


    ApiResult<Void> refuse(RefuseTaskRequest request);


    ApiResult<Void> sign(SignRequest request);


    ApiResult<Void> userTaskDeliver(UserTaskDeliverRequest request);

    ApiResult<Void> addAssignee(AddAssigneeRequest request);

    ApiResult<Void> finishTask(FinishTaskRequest request);

    ApiResult<Void> manualBack(ManualBackRequest request);

}
