package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import jakarta.validation.groups.Default;
import org.lg.engine.core.client.model.request.ProcessDetailRequest;
import org.lg.engine.core.client.model.request.ProcessListRequest;
import org.lg.engine.core.client.model.request.UpdateProcessRequest;
import org.lg.engine.core.client.model.response.ProcessDetailResponse;
import org.lg.engine.core.client.model.response.ProcessListResponse;
import org.lg.engine.core.client.model.response.ProcessUpdateResponse;
import org.lg.engine.core.client.service.ProcDefinitionService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.DeProcessMapper;
import org.lg.engine.core.db.service.RuTaskUserService;
import org.lg.engine.core.service.DeProcDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class ProcDefinitionServiceProvider implements ProcDefinitionService {

    @Resource
    private DeProcDefinitionService deProcDefinitionService;
    @Resource
    private RuTaskUserService ruTaskUserService;

    @Autowired
    private DeProcessMapper deProcessMapper;

    @Override
    public ApiResult<ProcessUpdateResponse> updateProcess(UpdateProcessRequest request) {
        Utils.validateEntity(request, Default.class);
        ProcessUpdateResponse response = deProcDefinitionService.updateProcess(request);
        return ApiResult.success(response);
    }


    @Override
    public ApiResult<ProcessDetailResponse> detail(ProcessDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        ProcessDetailResponse response = deProcDefinitionService.getProcessDetail(request);
        return ApiResult.success(response);
    }

    @Override
    public ApiResult<List<ProcessListResponse>> list(ProcessListRequest request) {
        Utils.validateEntity(request, Default.class);
        List<ProcessListResponse> processListResponses = deProcDefinitionService.list(request);
        return ApiResult.success(processListResponses);
    }

}
