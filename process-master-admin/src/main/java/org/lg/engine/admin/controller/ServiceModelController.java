package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import jakarta.validation.Valid;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.ReServiceModelPageResponse;
import org.lg.engine.core.client.model.response.ServiceModelResponse;
import org.lg.engine.core.client.service.ServiceModelService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 外部服务接口设置规则
 */
@RestController
@RequestMapping("service")
public class ServiceModelController {

    @Reference
    private ServiceModelService service;


    @GetMapping(name = "查询详情", value = "/detail")
    @Decrypt
    public ApiResult<ServiceModelResponse> detail(@Valid ServiceModelDetailRequest request ) {
        return service.detail(request);
    }


    @RequestMapping(name = "保存", value = "/edit", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> edit(@Valid @ModelAttribute ServiceModelRequest request) {
        service.edit(request);
        return ApiResult.success();
    }

    /**
     * 删除
     */
    @RequestMapping(name = "删除", value = "/del", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<Void> delete(@RequestParam(value = "key") String key) {
        service.delete(key);
        return ApiResult.success();
    }


    @RequestMapping(name = "列表", value = "/list", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<List<ServiceModelResponse>> list(@Valid ServiceModelQueryRequest request) {

        return service.list(request);
    }

    /**
     * 查询发布服务模型列表
     */
    @RequestMapping(name = "发布草稿服务模型", value = "/re/page", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<Page<ReServiceModelPageResponse>> pageReForm(@Valid ReServiceModelPageRequest request) {
        return service.pageReService(request);
    }
    /**
     * 查询发布服务模型列表
     */
    @RequestMapping(name = "发布草稿服务模型", value = "/re/enable", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> disableOrEnable(@Valid @ModelAttribute ReServiceModelDisableEnableRequest request) {
        return service.disableOrEnable(request);
    }


}

