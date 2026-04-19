package org.lg.engine.core.client.service;


//import org.lg.engine.core.client.model.ChildShapeRun;

import org.lg.engine.core.client.model.request.ProcessDetailRequest;
import org.lg.engine.core.client.model.request.ProcessListRequest;
import org.lg.engine.core.client.model.request.UpdateProcessRequest;
import org.lg.engine.core.client.model.response.ProcessDetailResponse;
import org.lg.engine.core.client.model.response.ProcessListResponse;
import org.lg.engine.core.client.model.response.ProcessUpdateResponse;
import org.lg.engine.core.client.utils.ApiResult;

import java.util.List;


public interface ProcDefinitionService {

    ApiResult<List<ProcessListResponse>> list(ProcessListRequest processListRequest);

    ApiResult<ProcessUpdateResponse> updateProcess(UpdateProcessRequest request);

    ApiResult<ProcessDetailResponse> detail(ProcessDetailRequest processDetailQuery);

}
