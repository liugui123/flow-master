package org.lg.engine.core.client.service;

import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;

import java.util.List;

public interface ServiceModelService {

    ApiResult<ServiceModelResponse> detail(ServiceModelDetailRequest request );

    ApiResult<Void> edit(ServiceModelRequest request);

    ApiResult<List<ServiceModelResponse>> list(ServiceModelQueryRequest request);

    /**
     * 分页查询发布表单
     */
    ApiResult<Page<ReServiceModelPageResponse>> pageReService(ReServiceModelPageRequest request);

    /**
     * 停用或者启用
     */
    ApiResult<Void> disableOrEnable(ReServiceModelDisableEnableRequest request);

    void delete(String serviceModelKey);
}
