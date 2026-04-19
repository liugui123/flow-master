package org.lg.engine.core.db.service;

import org.lg.engine.core.client.model.request.CheckProcessStartStrategyRequest;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.db.model.DeProcess;

import java.util.List;

public interface DeProcessService {


    int deleteByPrimaryKey(Long id);

    DeProcess selectByPrimaryKey(Long id);

    int updateByPrimaryKey(DeProcess record);

    int batchInsert(List<DeProcess> list);


    DeProcess selectFirstByProcKey(String procKey);

}



















































