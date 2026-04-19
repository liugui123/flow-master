package org.lg.engine.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.validation.Valid;
import org.lg.engine.admin.common.util.ApiCallExceptionUtil;
import org.lg.engine.admin.common.util.DeProcUtils;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.request.ProcessDetailByProcinstKeyRequest;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.client.model.request.RuprocinstUpdateRequest;
import org.lg.engine.core.client.model.response.ProcessDetailActStatus;
import org.lg.engine.core.client.model.response.ProcessDetailByProcResponse;
import org.lg.engine.core.client.model.response.ProcinstPageReponse;
import org.lg.engine.core.client.service.ProcinstService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程实例
 */
@RestController
@RequestMapping("/instance")
public class ProcinstController {

    @Reference
    private ProcinstService procinstService;

    /**
     * 流程实例列表
     */
    @RequestMapping(name = "流程实例列表", value = "/page", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<Page<ProcinstPageReponse>> page(@Valid ProcinstPageRequest procinstPageRequest) {
        ApiResult<Page<ProcinstPageReponse>> procinstPageReponse = procinstService.page(procinstPageRequest);
        return procinstPageReponse;
    }

    /**
     * 详情
     */
    @RequestMapping(name = "详情", value = "/detail", method = RequestMethod.GET)
    @Decrypt
    public ApiResult<ProcessDetailByProcResponse> detail(@Valid ProcessDetailByProcinstKeyRequest request) {
        ApiResult<ProcessDetailByProcResponse> res = procinstService.detail(request);
        if (!res.isSuccess()) {
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "获取流程实例详情错误");
            }
        }
        ProcessDetailByProcResponse detail = res.getData();

        List<ProcessDetailActStatus> statuses = detail.getStatuses();
        if (Utils.isNotEmpty(statuses)) {
            //已经通过的节点
            List<String> passedResourceIds = statuses.stream()
                    .filter(a -> a.getStatus() == 1)
                    .map(ProcessDetailActStatus::getActinstKey)
                    .collect(Collectors.toList());
            //正在运行的节点
            List<String> runningResourceIds = statuses.stream()
                    .filter(a -> a.getStatus() == 0)
                    .map(ProcessDetailActStatus::getActinstKey)
                    .collect(Collectors.toList());
            if (Utils.isNotEmpty(passedResourceIds)) {
                String procViewJson = detail.getProcViewJson();
                if (Utils.isNotEmpty(procViewJson)) {
                    //对已经通过的流程图，禁止点击处理
                    JSONObject jsonObject = JSON.parseObject(procViewJson);
                    JSONArray nodes = jsonObject.getJSONArray("nodes");
                    for (Object node : nodes) {
                        JSONObject object = (JSONObject) node;
                        String resourceId = object.getString("resourceId");
                        //重置状态
                        object.put("disabled", null);
                        object.put("running", null);
                        if (passedResourceIds.contains(resourceId)) {
                            //标记禁用节点
                            object.put("disabled", true);
                        }
                        if (runningResourceIds.contains(resourceId)) {
                            //标记运行节点
                            object.put("running", true);
                        }
                    }
                    JSONArray edges = jsonObject.getJSONArray("edges");
                    for (Object edge : edges) {
                        JSONObject object = (JSONObject) edge;
                        String resourceId = object.getString("resourceId");
                        if (passedResourceIds.contains(resourceId)) {
                            //标记禁用节点
                            object.put("disabled", true);
                        }
                        if (runningResourceIds.contains(resourceId)) {
                            //标记运行节点
                            object.put("running", true);
                        }
                    }
                    detail.setProcViewJson(JSON.toJSONString(jsonObject));
                }
            }
        }
        return res;
    }

    /**
     * 更新
     */
    @RequestMapping(name = "更新", value = "/edit", method = RequestMethod.POST)
    @Decrypt
    public ApiResult<Void> update(@Valid @ModelAttribute RuprocinstUpdateRequest request) {

        String procJson = DeProcUtils.convertView(request.getProcViewJson());
        request.setProcJson(procJson);

        ApiResult<Void> res = procinstService.update(request);
        if (!res.isSuccess()) {
            if (res.getCode() >= -500 && res.getCode() <= -100) {
                return ApiResult.fail(res.getMsg(), res.getCode());
            } else {
                ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(res, "更新流程实例错误");
            }
        }
        return ApiResult.success();
    }
}
