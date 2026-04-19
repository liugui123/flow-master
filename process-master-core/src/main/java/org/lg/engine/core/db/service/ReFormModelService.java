package org.lg.engine.core.db.service;

import org.lg.engine.core.client.model.request.ReFormModelPageRequest;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.db.model.ReFormModel;

import java.util.List;
import java.util.Map;

public interface ReFormModelService {


    int deleteByPrimaryKey(Long id);

    int insert(ReFormModel record);

    ReFormModel selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ReFormModel record);

    int batchInsert(List<ReFormModel> list);

    Map<Long, ReFormModel> getIdAndFormModelRel(List<Long> reModelIds);

    Page<ReFormModel> page(ReFormModelPageRequest request);

    List<ReFormModel> selectMaxVersionFormByAppKey(String formModelName, String appKey);

    void disableOrEnable(Integer status, Long id);


    Integer selectFirstModelVersionByModelKeyOrderByModelVersionDesc(String modelKey);

    ReFormModel selectFirstByFormModelKeyOrderByModelVersionDesc(String formModelKey);
}










