package org.lg.engine.admin.service.app;


import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.DeleteAppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.utils.ApiResult;

import java.util.List;

/**
 * 应用详情服务
 */
public interface AppDetailService {

    /**
     * 保存当前的分组
     *
     * @return
     */
    ApiResult<Void> edit(SaveAppManageRequest request);

    /**
     * Pc端查询应用树
     *
     * @param appDetailRequest 入参
     * @return 应用树
     */
    ApiResult<List<AppGroupDetailPcResponse>> appDetailTree(AppDetailRequest appDetailRequest);

    /**
     * 删除应用详情
     */
    void delete(DeleteAppDetailRequest request);

}
