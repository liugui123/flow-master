package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import jakarta.validation.groups.Default;
import org.lg.engine.core.aspect.RepeatSubmit;
import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.DeleteAppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.service.AppDetailService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class AppDetailServiceProvider implements AppDetailService {

    @Resource
    private org.lg.engine.core.db.service.AppDetailService appDetailService;

    @RepeatSubmit
    @Override
    public ApiResult<Void> edit(SaveAppManageRequest request) {
        Utils.validateEntity(request, Default.class);
        appDetailService.edit(request);
        return ApiResult.success();
    }


    @Override
    public ApiResult<List<AppGroupDetailPcResponse>> appDetailTreeForPc(AppDetailRequest appDetailRequest) {
        List<AppGroupDetailPcResponse> appGroupDetailPcResponses = appDetailService
                .appDetailTree(appDetailRequest);
        return ApiResult.success(appGroupDetailPcResponses);
    }

    @Override
    public ApiResult<Void> deleteByAppDetailKey(DeleteAppDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        appDetailService.deleteByAppDetailKey(request.getAppDetailKey());
        return ApiResult.success();
    }
}
