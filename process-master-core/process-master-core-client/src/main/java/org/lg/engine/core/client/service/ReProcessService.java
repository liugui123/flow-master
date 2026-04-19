package org.lg.engine.core.client.service;


import org.lg.engine.core.client.model.request.StartConfRequest;
import org.lg.engine.core.client.utils.ApiResult;


public interface ReProcessService {

    ApiResult<String> startActConf(StartConfRequest request);

}
