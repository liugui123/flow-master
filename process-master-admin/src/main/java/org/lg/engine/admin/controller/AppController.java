package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import jakarta.validation.Valid;
import org.lg.engine.admin.common.util.ApiCallExceptionUtil;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.AppDelRequest;
import org.lg.engine.core.client.model.request.AppQueryRequest;
import org.lg.engine.core.client.model.request.UpdateAppRequest;
import org.lg.engine.core.client.model.response.AppResponse;
import org.lg.engine.core.client.utils.ApiResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用中心
 */
@RestController
@RequestMapping("/app")
public class AppController {

    /**
     * 应用中心管理组件
     */
    @Reference
    private org.lg.engine.core.client.service.AppService appService;

    /**
     * 应用中心列表
     *
     * @param
     * @return
     */
    @RequestMapping(name = "应用中心列表", value = "/list", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<List<AppResponse>> list(Operator operator) {
        ApiResult<List<AppResponse>> listByOperator = appService.getListByOperator(operator);
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(listByOperator, "查询用户应用树错误");
        return listByOperator;
    }


    /**
     * 修改应用
     */
    @RequestMapping(name = "修改列表", value = "/edit", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> update(@Valid @ModelAttribute UpdateAppRequest request) {
        return appService.update(request);
    }

    /**
     * 查询单个应用
     */
    @RequestMapping(name = "查询单个应用", value = "/detail", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<AppResponse> query(@Valid @ModelAttribute AppQueryRequest appQuery) {
        ApiResult<AppResponse> apiResult = appService.getByAppKey(appQuery);
        return apiResult;
    }

    /**
     * 删除应用
     */
    @RequestMapping(name = "删除应用", value = "/delete", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> delete(@Valid @ModelAttribute AppDelRequest request) {

        return appService.delete(request);
    }
}
