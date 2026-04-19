package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.ReFormModelDetail;

import java.util.Collection;
import java.util.List;

public interface ReFormModelDetailService {


    int deleteByPrimaryKey(Long id);

    int insert(ReFormModelDetail record);

    ReFormModelDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ReFormModelDetail record);

    int batchInsert(List<ReFormModelDetail> list);


    List<ReFormModelDetail> selectByFormModelId(Long formModelId);

    List<ReFormModelDetail> selectByFormModelIdIn(Collection<Long> formModelIdCollection);

    void updateStatusById(Integer updatedStatus, Long id);
}


