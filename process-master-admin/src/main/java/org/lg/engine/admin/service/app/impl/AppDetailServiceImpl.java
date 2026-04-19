package org.lg.engine.admin.service.app.impl;


//import com.shinemo.baas.baas.api.form.FormMetaInfoDto;

import com.alibaba.dubbo.config.annotation.Reference;
import org.lg.engine.admin.common.util.ApiCallExceptionUtil;
import org.lg.engine.admin.service.app.AppDetailService;
import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.DeleteAppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.utils.ApiResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用管理组件
 *
 * @Author hezhenyang
 */
@Service
public class AppDetailServiceImpl implements AppDetailService {

    @Reference
    private org.lg.engine.core.client.service.AppDetailService appDetailService;

    @Reference
    private org.lg.engine.core.client.service.ProcDefinitionService procDefinitionService;

    @Override
    public ApiResult<Void> edit(SaveAppManageRequest request) {
        return appDetailService.edit(request);
    }

    @Override
    public ApiResult<List<AppGroupDetailPcResponse>> appDetailTree(AppDetailRequest appDetailRequest) {
        ApiResult<List<AppGroupDetailPcResponse>> listApiResult = appDetailService
                .appDetailTreeForPc(appDetailRequest);
        ApiCallExceptionUtil.ifErrorLogsAndThrowApiException(listApiResult, "查询应用树错误");
        return listApiResult;
    }

    @Override
    public void delete(DeleteAppDetailRequest request) {
        appDetailService.deleteByAppDetailKey(request);

    }

}
