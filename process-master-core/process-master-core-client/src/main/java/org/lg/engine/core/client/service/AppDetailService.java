package org.lg.engine.core.client.service;


import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.DeleteAppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.utils.ApiResult;

import java.util.List;


public interface AppDetailService {


    ApiResult<Void> edit(SaveAppManageRequest saveAppManageRequest);

    /**
     * 查询已经发布的应用详情内容
     */
    ApiResult<List<AppGroupDetailPcResponse>> appDetailTreeForPc(AppDetailRequest appDetailRequest);


    ApiResult<Void> deleteByAppDetailKey(DeleteAppDetailRequest deleteAppDetailRequest);

}
