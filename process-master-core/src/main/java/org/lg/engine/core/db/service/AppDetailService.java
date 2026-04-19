package org.lg.engine.core.db.service;

import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.db.model.AppDetail;

import java.util.List;

public interface AppDetailService {


    int deleteByPrimaryKey(Long id);


    AppDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKey(AppDetail record);


    void edit(SaveAppManageRequest saveAppManageRequest);


    void deleteByAppDetailKey(String appDetailKey);

    List<AppGroupDetailPcResponse> appDetailTree(AppDetailRequest appDetailRequest);

}



