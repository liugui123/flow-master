package org.lg.engine.admin.controller;

import jakarta.validation.Valid;
import org.lg.engine.admin.service.app.AppDetailService;
import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.DeleteAppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.utils.ApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 应用详情
 *
 * @author wjp
 * @data 2021/07/7 16:50
 */
@RestController
@RequestMapping("app/content")
public class AppContentController {

    @Resource
    private AppDetailService appDetailService;

    /**
     * 当前应用的访问-已发布、草稿 pc
     */
    @GetMapping(name = "查询应用详情", value = "/detail")
    @Decrypt
    public ApiResult<List<AppGroupDetailPcResponse>> detail(@Valid AppDetailRequest request) {
        return appDetailService.appDetailTree(request);
    }

    /**
     * 保存应用管理
     */
    @RequestMapping(name = "保存应用管理", value = "/edit", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> edit(@Valid @ModelAttribute SaveAppManageRequest request) {
        return appDetailService.edit(request);
    }

    /**
     * 删除
     */
    @RequestMapping(name = "删除", value = "/del", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> delete(@Valid @ModelAttribute DeleteAppDetailRequest request) {
        appDetailService.delete(request);
        return ApiResult.success();
    }

}

