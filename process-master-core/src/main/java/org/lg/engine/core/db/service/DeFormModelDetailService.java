package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.DeFormModelDetail;

import java.util.List;

public interface DeFormModelDetailService {


    int deleteByPrimaryKey(Long id);

    int insert(DeFormModelDetail record);

    DeFormModelDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKey(DeFormModelDetail record);

    int batchInsert(List<DeFormModelDetail> list);


    int deleteByFormModelId(Long formModelId);


    List<DeFormModelDetail> selectByFormModelId(Long formModelId);
}

