package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import jakarta.validation.groups.Default;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.request.StartConfRequest;
import org.lg.engine.core.client.service.ReProcessService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service
@Component
public class ReProcessServiceProvider implements ReProcessService {

    @Resource
    private org.lg.engine.core.db.service.ReProcessService reProcessService;

    @Override
    public ApiResult<String> startActConf(StartConfRequest request) {
        Utils.validateEntity(request, Default.class);
        ChildShape childShape = reProcessService.startActConf(request);
        return ApiResult.success(JSON.toJSONString(childShape));
    }
}
