package org.lg.engine.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import jakarta.validation.Valid;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.AutoCompleteTaskResponse;
import org.lg.engine.core.client.model.response.ManualCompleteTaskResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.core.client.service.ProcessAuthService;
import org.lg.engine.core.client.service.WfExeService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.portal.common.utils.ApiCallExceptionUtil;
import org.lg.engine.portal.common.utils.Logs;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程任务流转
 *
 * @author liugui
 * @date 2021/04/28 17:36
 */
@RestController
@RequestMapping("exe")
public class ExeController {

    /**
     * 任务流转服务
     */
    @Reference
    private WfExeService wfExeService;
    @Reference
    private ProcessAuthService processAuthService;

    @PostMapping(name = "自动启动流程", value = "auto/start")
    @Decrypt
    public ApiResult<Void> start(@Valid @ModelAttribute AutoStartProcRequest startProcRequest) {
        ApiResult<StartProcessResponse> res = wfExeService.autoStartProcess(startProcRequest);
        if (!res.isSuccess()) {
            Logs.error("启动流程错误autoStartProcess:入参：{}，出参：{}", JSON.toJSONString(startProcRequest), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "启动流程错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "手动启动流程", value = "manual/start")
    @Decrypt
    public ApiResult<Void> start(@Valid @ModelAttribute ManualStartProcRequest startProcRequest) {
        ApiResult<StartProcessResponse> res = wfExeService.manualStartProcess(startProcRequest);
        if (!res.isSuccess()) {
            Logs.error("启动流程错误manualStartProcess:入参：{}，出参：{}", JSON.toJSONString(startProcRequest), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "启动流程错误");
            }
        }
        return ApiResult.success();
    }

    /**
     * 保存启动草稿
     */
    @PostMapping(name = "保存启动草稿", value = "start/draft")
    @Decrypt
    public ApiResult<Void> startDraft(@Valid @ModelAttribute SaveStartDraftRequest request) {
        ApiResult<Void> res = wfExeService.saveStartDraft(request);
        if (!res.isSuccess()) {
            Logs.error("保存启动草稿错误autoStartProcess:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "保存启动草稿错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "删除启动草稿", value = "start/draft/del")
    @Decrypt
    public ApiResult<Void> delStartDraft(@Valid @ModelAttribute DelStartDraftRequest request) {
        ApiResult<Void> res = wfExeService.delStartDraft(request);
        if (!res.isSuccess()) {
            Logs.error("删除启动草稿错误autoStartProcess:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "删除启动草稿错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "自动完成个人待办", value = "user/task/auto/complete")
    @Decrypt
    public ApiResult<Void> complete(@Valid @ModelAttribute AutoCompleteTaskRequest request) {
        ApiResult<AutoCompleteTaskResponse> res = wfExeService.autoCompleteTask(request);
        if (!res.isSuccess()) {
            Logs.error("自动完成个人待办错误autoCompleteTask:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "完成待办错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "手动完成个人待办", value = "user/task/manual/complete")
    @Decrypt
    public ApiResult<Void> complete(@Valid @ModelAttribute ManualCompleteTaskRequest request) {
        ApiResult<ManualCompleteTaskResponse> res = wfExeService.manualCompleteTask(request);
        if (!res.isSuccess()) {
            Logs.error("手动完成个人待办manualCompleteTask:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "完成待办错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "拒绝", value = "task/refuse")
    @Decrypt
    public ApiResult<Void> refuse(@Valid @ModelAttribute RefuseTaskRequest request) {
        ApiResult<Void> res = wfExeService.refuse(request);
        if (!res.isSuccess()) {
            Logs.error("拒绝错误refuse:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "任务拒绝错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "完成个人待阅任务", value = "user/task/read/complete")
    @Decrypt
    public ApiResult<Void> finish(@Valid @ModelAttribute FinishTaskRequest request) {
        ApiResult<Void> res = wfExeService.finishTask(request);
        if (!res.isSuccess()) {
            Logs.error("完成个人待阅任务错误finishTask:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "手动完成个人待阅任务错误");
            }
        }
        return ApiResult.success();
    }

    /**
     * 流程撤销
     */
    @PostMapping(name = "流程撤销", value = "user/task/revoke")
    @Decrypt
    public ApiResult<Void> revoke(@Valid @ModelAttribute RevokeTaskRequest request) {
        ApiResult<Void> res = wfExeService.revoke(request);
        if (!res.isSuccess()) {
            Logs.error("流程撤销错误revoke:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "流程撤销错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "任务添加办理人", value = "task/assignee/add")
    @Decrypt
    public ApiResult<Void> addAssignee(@Valid @ModelAttribute AddAssigneeRequest request) {
        ApiResult<Void> res = wfExeService.addAssignee(request);
        if (!res.isSuccess()) {
            Logs.error("任务添加办理人错误addAssignee:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "添加办理人错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "用户任务转交", value = "user/task/deliver")
    @Decrypt
    public ApiResult<Void> userTaskDeliver(@Valid @ModelAttribute UserTaskDeliverRequest request) {
        ApiResult<Void> res = wfExeService.userTaskDeliver(request);
        if (!res.isSuccess()) {
            Logs.error("用户任务转交错误userTaskDeliver:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "用户任务转交错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "流程退回", value = "user/task/back")
    @Decrypt
    public ApiResult<Void> back(@Valid @ModelAttribute ManualBackRequest request) {
        ApiResult<Void> res = wfExeService.manualBack(request);
        if (!res.isSuccess()) {
            Logs.error("流程退回错误manualBack:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "流程退回错误");
            }
        }
        return ApiResult.success();
    }

    @PostMapping(name = "流程加签", value = "user/task/add/sign")
    @Decrypt
    public ApiResult<Void> sign(@Valid @ModelAttribute SignRequest request) {
        ApiResult<Void> res = wfExeService.sign(request);
        if (!res.isSuccess()) {
            Logs.error("流程加签错误refuse:入参：{}，出参：{}", JSON.toJSONString(request), JSON.toJSONString(res));
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "流程加签错误");
            }
        }
        return ApiResult.success();
    }

}
