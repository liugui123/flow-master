package org.lg.engine.core.service;

import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.CompleteTaskResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;


public interface RunTimeService {
    /**
     * 保存启动草稿
     */
    void saveStartDraft(SaveStartDraftRequest request);

    /**
     * 删除启动草稿
     */
    void delStartDraft(DelStartDraftRequest request);

    /**
     * 启动流程
     */
    StartProcessResponse startProcess(StartProcRequest request);

    /**
     * 自动完成个人待办/收到完成待办任务
     */
    CompleteTaskResponse completeTask(CompleteTaskRequest request);

    /**
     * 撤销流程
     */
    void revoke(RevokeTaskRequest request);

    /**
     * 退回节点
     */
    void back(ManualBackRequest request);

    /**
     * 拒绝流程
     */
    void refuse(RefuseTaskRequest request);

    /**
     * 加签
     */
    void sign(SignRequest sign);

    /**
     * 用户任务转交
     */
    void userTaskDeliver(UserTaskDeliverRequest request);

    /**
     * 任务添加处理人
     */
    void taskAddAssignee(AddAssigneeRequest request);

    /**
     * 结束用户任务
     */
    void finishTask(FinishTaskRequest request);

    /**
     * 管理端-更新流程实例
     */
    void ruProcinstUpdate(RuprocinstUpdateRequest request);

}
