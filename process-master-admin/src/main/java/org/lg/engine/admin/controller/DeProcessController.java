package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import jakarta.validation.Valid;
import org.lg.engine.admin.common.util.ApiCallExceptionUtil;
import org.lg.engine.admin.common.util.Convert;
import org.lg.engine.admin.common.util.DeProcUtils;
import org.lg.engine.admin.model.updateprocess.UpdateProcessDTO;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.ProcessDetailRequest;
import org.lg.engine.core.client.model.request.ProcessListRequest;
import org.lg.engine.core.client.model.request.UpdateProcessRequest;
import org.lg.engine.core.client.model.response.ProcessDetailResponse;
import org.lg.engine.core.client.model.response.ProcessListResponse;
import org.lg.engine.core.client.model.response.ProcessUpdateResponse;
import org.lg.engine.core.client.service.ProcDefinitionService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Results;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义管理
 *
 * @author liugui
 * @date 2021/06/16 17:11
 */
@RestController
@RequestMapping("/chat")
public class DeProcessController {

    @Reference
    private ProcDefinitionService procDefinitionService;

    /**
     * 分页查询列表
     */
    @RequestMapping(name = "应用中心列表", value = "/page", method = RequestMethod.GET)
    @Decrypt
    public Results<? extends List<? extends Object>> getProcessList(@Valid ProcessListRequest request) {
        ApiResult<List<org.lg.engine.core.client.model.response.ProcessListResponse>> data = procDefinitionService.list(request);
        if (data.isSuccess()) {
            return ApiResult.success(data.getData());
        }
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(data, "应用中心列表");
        return ApiResult.success(new ArrayList<>(0));
    }

    /**
     * 修改流程模型
     */
    @RequestMapping(name = "修改流程模型", value = "/update", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> update(@Valid @ModelAttribute UpdateProcessDTO dto) {
        //前端数据转换
        String procJson = DeProcUtils.convertView(dto.getProcViewJson());

        UpdateProcessRequest request = Convert.INSTANCE.updateProcessDtoToReq(dto);
        request.setProcJson(procJson);

        request.setFormModelReId(dto.getFormModelReId());
        ApiResult<ProcessUpdateResponse> processUpdateResponseApiResult = procDefinitionService.updateProcess(request);
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(processUpdateResponseApiResult, "修改流程定义错误");

        return ApiResult.success();
    }

    /**
     * 查询流程模型
     */
    @RequestMapping(name = "查询流程模型", value = "/detail", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<ProcessDetailResponse> query(@Valid ProcessDetailRequest request) {
        ApiResult<ProcessDetailResponse> detail = procDefinitionService.detail(request);
        ProcessDetailResponse processDetailResponse = detail.getData();
        return ApiResult.success(processDetailResponse);
    }
}
