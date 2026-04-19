package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.AdminApp;

import java.util.List;

public interface AdminAppService {


    int deleteByPrimaryKey(Long id);

    int insert(AdminApp record);

    AdminApp selectByPrimaryKey(Long id);

    int updateByPrimaryKey(AdminApp record);

    int batchInsert(List<AdminApp> list);
}




