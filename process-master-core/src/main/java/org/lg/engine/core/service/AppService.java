package org.lg.engine.core.service;

import org.lg.engine.core.client.model.request.UpdateAppRequest;
import org.lg.engine.core.db.model.AdminApp;

public interface AppService {

    void update(UpdateAppRequest updateAppRequest);

    void delete(String appKey);

    /**
     * 查询key对应的id
     *
     * @param appKey 应用key
     * @return id
     */
    Long selectFirstIdByAppKey(String appKey);

    /**
     * 查询key对应的id,name
     *
     * @param appKey
     * @return
     */
    AdminApp selectFirstByAppKey(String appKey);

}
