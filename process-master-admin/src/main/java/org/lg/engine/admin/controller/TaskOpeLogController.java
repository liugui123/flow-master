/**
 * 任务操作日志记录
 *
 * @author liugui
 * @date 2021/06/04 17:17
 **/
package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.lg.engine.core.client.model.request.TaskOpeLogPageRequest;
import org.lg.engine.core.client.model.response.TaskOpeLogPageResponse;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.service.TaskOperateLogService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 任务操作日志记录
 */
@RestController
@RequestMapping("task/log")
public class TaskOpeLogController {

    @Reference
    private TaskOperateLogService taskOperateLogService;

    /**
     * 任务操作日志
     */
    @RequestMapping(name = "任务操作日志", value = "/page", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Page<TaskOpeLogPageResponse>> page(@ModelAttribute TaskOpeLogPageRequest request) {
        return taskOperateLogService.page(request);
    }

}
