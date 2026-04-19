package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import jakarta.validation.Valid;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.client.service.FormService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 表单控制
 */
@RestController
@RequestMapping("form")
public class FormController {

    /**
     * 应用中心管理组件
     */
    @Reference
    private FormService formService;


    /**
     * 添加或修改草稿表单模型
     */
    @RequestMapping(name = "添加或修改草稿表单模型", value = "/edit", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> edit(@Valid @ModelAttribute EditDeFromModelAndDetailRequest request) {
        return formService.edit(request);
    }

    /**
     * 查询表单模型草稿列表
     */
    @RequestMapping(name = "发布草稿表单模型", value = "/list", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<List<DeFormModelListResponse>> deFromModelList(@Valid DeFormModelListRequest request) {
        return formService.deFromModelList(request);
    }

    /**
     * 草稿表单模型详情
     *
     * @return
     */
    @RequestMapping(name = "草稿表单模型详情", value = "/detail", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<DeFromModelDetailResponse> deFromModelDetail(@Valid DeFormModelDetailRequest request) {
        return formService.detail(request);
    }

    /**
     * 查询最大版本的发布表单模型列表
     */
    @RequestMapping(name = "发布草稿表单模型", value = "/latest/re/list", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<List<ReFormLatestModelListResponse>> latestReFromModelList(@Valid ReFormLatestModelListRequest request) {
        return formService.latestReFromModelList(request);
    }

    /**
     * 查询发布表单模型列表
     */
    @RequestMapping(name = "发布草稿表单模型", value = "/re/page", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<Page<ReFormModelPageResponse>> pageReForm(@Valid ReFormModelPageRequest request) {
        return formService.pageReForm(request);
    }
    /**
     * 查询发布表单模型列表
     */
    @RequestMapping(name = "发布草稿表单模型", value = "/re/enable", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> disableOrEnable(@Valid @ModelAttribute ReFormModelDisableEnableRequest request) {
        return formService.disableOrEnable(request);
    }

    /**
     * 已经发布的表单模型详情
     */
    @RequestMapping(name = "发布草稿表单模型", value = "/reform/detail", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<List<ReFormModelDetailResponse>> reFromModelDetail(@Valid ReFormModelDetailRequest request) {
        return formService.reFromModelDetail(request);
    }

    /**
     * key查询最新发布的模型信息
     */
    @RequestMapping(name = "key查询最新发布的模型信息", value = "/reform/detail/key", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<ReFormModelDetailByKeyResponse> selectFirstByFormModelKeyOrderByModelVersionDesc(@Valid ReFormModelDetailByKeyRequest request) {
        return formService.selectFirstByFormModelKeyOrderByModelVersionDesc(request);
    }
}
