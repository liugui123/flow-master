package org.lg.engine.core.client.service;



import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;

import java.util.List;


public interface FormService {

    /**
     * 编辑
     */
    ApiResult<Void> edit(EditDeFromModelAndDetailRequest request);

    /**
     * 详情
     */
    ApiResult<DeFromModelDetailResponse> detail(DeFormModelDetailRequest request);

    ApiResult<List<DeFormModelListResponse>> deFromModelList(DeFormModelListRequest request);

    /**
     * 查询已经发布的表单及详情
     */
    ApiResult<List<ReFormLatestModelListResponse>> latestReFromModelList(ReFormLatestModelListRequest request);

    /**
     * 分页查询发布表单
     */
    ApiResult<Page<ReFormModelPageResponse>> pageReForm(ReFormModelPageRequest request);

    /**
     * 停用或者启用
     */
    ApiResult<Void> disableOrEnable(ReFormModelDisableEnableRequest request);


    ApiResult<List<ReFormModelDetailResponse>> reFromModelDetail(ReFormModelDetailRequest request);

    ApiResult<ReFormModelDetailByKeyResponse> selectFirstByFormModelKeyOrderByModelVersionDesc(ReFormModelDetailByKeyRequest request);


    /**
     * 发布草稿表单模型
     */
    ApiResult<Void> publishDeFromModel(PublishDeFromModelAndDetailRequest request);


}
