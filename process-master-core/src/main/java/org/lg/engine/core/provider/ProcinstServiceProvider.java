package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import jakarta.validation.groups.Default;
import org.lg.engine.core.aspect.RepeatSubmit;
import org.lg.engine.core.client.model.request.ProcessDetailByProcinstKeyRequest;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.client.model.request.RuprocinstUpdateRequest;
import org.lg.engine.core.client.model.response.ProcessDetailByProcResponse;
import org.lg.engine.core.client.model.response.ProcinstPageReponse;
import org.lg.engine.core.client.service.ProcinstService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuActinstMapper;
import org.lg.engine.core.db.service.RuProcinstService;
import org.lg.engine.core.service.RunTimeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//import org.lg.engine.core.service.TodoCenterService;


@Service
@Component
public class ProcinstServiceProvider implements ProcinstService {

    @Resource
    private RuProcinstService ruProcinstService;

    @Resource
    private RuActinstMapper ruActinstMapper;

    @Resource
    private RunTimeService runTimeService;

    @Override
    public ApiResult<Page<ProcinstPageReponse>> page(ProcinstPageRequest request) {
        Page<ProcinstPageReponse> res = ruProcinstService.page(request);
        return ApiResult.success(res);
    }

    @Override
    public ApiResult<ProcessDetailByProcResponse> detail(ProcessDetailByProcinstKeyRequest request) {
        Utils.validateEntity(request, Default.class);
        ProcessDetailByProcResponse response = ruProcinstService.detail(request);
        return ApiResult.success(response);
    }

    @Override
    @RepeatSubmit
    public ApiResult<Void> update(RuprocinstUpdateRequest request) {
        Utils.validateEntity(request, Default.class);
        runTimeService.ruProcinstUpdate(request);
        return ApiResult.success();
    }
}
