package org.lg.engine.core.client.service;

import org.lg.engine.core.client.model.request.ProcessDetailByProcinstKeyRequest;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.client.model.request.RuprocinstUpdateRequest;
import org.lg.engine.core.client.model.response.ProcessDetailByProcResponse;
import org.lg.engine.core.client.model.response.ProcinstPageReponse;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;


public interface ProcinstService {
    /**
     * 分页
     */
    ApiResult<Page<ProcinstPageReponse>> page(ProcinstPageRequest request);

    /**
     * 详情
     */
    ApiResult<ProcessDetailByProcResponse> detail(ProcessDetailByProcinstKeyRequest request);

    ApiResult<Void> update(RuprocinstUpdateRequest request);

}
