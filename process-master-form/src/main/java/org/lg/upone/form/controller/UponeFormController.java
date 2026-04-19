package org.lg.upone.form.controller;

import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.lg.upone.form.model.req.*;
import org.lg.upone.form.model.res.*;
import org.lg.upone.form.service.UponeFormService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (t_upone_de_form)表控制层
 */
@RestController
@RequestMapping("/form")
public class UponeFormController {

    @Resource
    private UponeFormService uponeFormService;

    /**
     * 草稿列表
     */
    @GetMapping(value = "/de/page")
    @Decrypt
    public ApiResult<Page<DraftFormRes>> draft(@Valid PageFormReq request) {
        Page<DraftFormRes> page = uponeFormService.draft(request);
        return ApiResult.success(page);
    }

    /**
     * 发布列表
     */
    @GetMapping(value = "/re/page")
    @Decrypt
    public ApiResult<Page<PublishFormRes>> publish(@Valid PageFormReq request) {
        Page<PublishFormRes> publish = uponeFormService.publish(request);
        return ApiResult.success(publish);
    }

    /**
     * 发布表单复制为草稿
     */
    @PostMapping(value = "/re/copy")
    @Decrypt
    public ApiResult<Void> copy(@Valid @ModelAttribute ReFormCopyReq request) {
        uponeFormService.copy(request);
        return  ApiResult.success();
    }

    /**
     * 流程实例列表
     */
    @GetMapping(value = "/de/detail")
    @Decrypt
    public ApiResult<DetailFormRes> detail(@Valid DetailFormReq request) {
        DetailFormRes res = uponeFormService.detail(request);
        return ApiResult.success(res);
    }

    /**
     * 流程实例列表
     */
    @GetMapping(value = "/ru/detail")
    @Decrypt
    public ApiResult<DetailRuFormRes> detail(@Valid DetailRuFormReq request) {
        DetailRuFormRes res = uponeFormService.ruFormDetail(request);
        return ApiResult.success(res);
    }

    /**
     * 流程实例列表
     */
    @GetMapping(value = "/de/del")
    @Decrypt
    public ApiResult del(@Valid DetailFormReq request) {
        uponeFormService.del(request);
        return ApiResult.success();
    }

    /**
     * 编辑表单
     */
    @PostMapping("/de/edit")
    @Decrypt
    public ApiResult<EditFormRes> edit(@Valid @ModelAttribute EditFormReq request) {

        EditFormRes res = uponeFormService.edit(request);

        return ApiResult.success(res);
    }

    /**
     * 编辑表单
     */
    @PostMapping("/ru/edit")
    @Decrypt
    public ApiResult<EditRuFormRes> ruFormEdit(@Valid @ModelAttribute EditRuFormReq request) {

        EditRuFormRes res = uponeFormService.ruFormEdit(request);

        return ApiResult.success(res);
    }

}
