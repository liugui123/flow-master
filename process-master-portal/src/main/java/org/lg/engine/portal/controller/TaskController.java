package org.lg.engine.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import jakarta.validation.Valid;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.client.service.ReProcessService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.portal.common.utils.ApiCallExceptionUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 任务中心
 *
 * @author liugui
 * @date 2021/04/28 17:36
 */
@RestController
@RequestMapping("task")
public class TaskController {

    /**
     * 发布流程服务
     */
    @Reference
    private ReProcessService reProcessService;

    @Reference
    private TaskService taskService;

    @Reference
    private ActinstService actinstService;

    /**
     * 我的发起草稿
     */
    @PostMapping(name = "我的发起草稿", value = "start/draft")
    @Decrypt
    public ApiResult<Page<StartDraftResponse>> startDraft(@Valid @ModelAttribute MyStartDraftRequest request) {
        return taskService.pageMyStartDrafts(request);
    }

    /**
     * 我的待办任务
     */
    @PostMapping(name = "我的待办任务", value = "todo")
    @Decrypt
    public ApiResult<Page<UserTaskResponse>> pagePendingUserTasks(@Valid @ModelAttribute PendingUserTaskRequest request) {
//        PendingUserTaskRequest request = Convert.INSTANCE.dtoToPendingUserTaskRequest(dto);
//        request.setOperator(OperatorFactory.getOperator());
        return taskService.pagePendingUserTasks(request);
    }

    /**
     * 我的已办任务
     */
    @PostMapping(name = "我的已办任务", value = "done")
    @Decrypt
    public ApiResult<Page<UserTaskResponse>> pageDoneUserTasks(@Valid @ModelAttribute DoneUserTaskRequest request) {
//        DoneUserTaskRequest request = Convert.INSTANCE.dtoToDoneUserTaskRequest(dto);
//        request.setOperator(OperatorFactory.getOperator());
        return taskService.pageDoneUserTasks(request);
    }

    /**
     * 抄送我的任务
     */
    @PostMapping(name = "抄送我的任务", value = "copy")
    @Decrypt
    public ApiResult<Page<UserTaskResponse>> pageCopyUserTasks(@Valid @ModelAttribute CopyUserTaskRequest request) {
//        CopyUserTaskRequest request = Convert.INSTANCE.dtoToCopyUserTaskRequest(dto);
//        request.setOperator(OperatorFactory.getOperator());
        return taskService.pageCopyUserTasks(request);
    }

    /**
     * 我发起的任务
     */
    @PostMapping(name = "我发起的任务", value = "start")
    @Decrypt
    public ApiResult<Page<UserTaskResponse>> pageMyStartUserTasks(@Valid @ModelAttribute MyStartUserTaskRequest request) {
        return taskService.pageMyStartUserTasks(request);
    }


    /**
     * 打开启动流程页面 显示流程的节点配置，按钮配置，流程意见列表
     */
    @PostMapping(name = "打开启动流程页面", value = "start/open")
    @Decrypt
    public ApiResult<String> openStart(@Valid @ModelAttribute StartConfRequest request) {
        ApiResult<String> apiResult = reProcessService.startActConf(request);
        if (!apiResult.isSuccess()) {
            if (apiResult.getCode() >= -500 && apiResult.getCode() <= -100) {
                return ApiResult.fail(apiResult.getMsg(), apiResult.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(apiResult, "打开启动流程页面错误");
            }
        }
        return ApiResult.success(apiResult.getData());
    }

    /**
     * 打开任务页面 打开待办
     */
    @PostMapping(name = "打开任务页面", value = "/open")
    @Decrypt
    public ApiResult<OpenUserTaskResponse> open(@Valid @ModelAttribute OpenUserTaskRequest request) {
        ApiResult<OpenUserTaskResponse> apiResult = taskService.openUserTask(request);
        if (!apiResult.isSuccess()) {
            if (apiResult.getCode() >= -500 && apiResult.getCode() <= -100) {
                return ApiResult.fail(apiResult.getMsg(), apiResult.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(apiResult, "打开启动流程页面失败");
            }
        }
        OpenUserTaskResponse openTaskParamResponse = apiResult.getData();
        return ApiResult.success(openTaskParamResponse);
    }

    /**
     * 查询节点人员树
     */
    @PostMapping(name = "标记抄送为已读", value = "/users")
    @Decrypt
    public ApiResult<TaskUserTreeResponse> read(@Valid @ModelAttribute TaskUsersRequest request) {
        return taskService.taskUsers(request);
    }

    @PostMapping(name = "当前任务可退回的历史节点实例列表", value = "back/acts")
    @Decrypt
    public ApiResult<List<TaskBackActsResponse>> backAbleActs(@Valid @ModelAttribute TaskBackActsRequest request) {
        return actinstService.backableActs(request);
    }

    /**
     * 获取开始节点的下个节点列表
     */
    @PostMapping(name = "获取开始节点的下个节点列表", value = "start/next")
    @Decrypt
    public ApiResult<List<NextActForStartResponse>> nextDeActListForStartAct(@Valid @ModelAttribute DeActNextListForStartActRequest request) {
        ApiResult<List<NextActForStartResponse>> apiResult = actinstService.nextDeActListForStartAct(request);
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(apiResult, "获取开始节点的下个可选列表错误");
        return ApiResult.success(apiResult.getData());
    }

    /**
     * 获取当前节点的下个可选节点列表
     */
    @PostMapping(name = "获取当前节点的下个可选节点列表", value = "next")
    @Decrypt
    public ApiResult<List<NextActResponse>> nextActinstList(@Valid @ModelAttribute ActinstNextListRequest request) {
        ApiResult<List<NextActResponse>> listApiResult = actinstService.nextActList(request);
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(listApiResult, "获取当前节点的下个可选节点列表错误");
        List<NextActResponse> data = listApiResult.getData();
        return ApiResult.success(data);
    }

}
