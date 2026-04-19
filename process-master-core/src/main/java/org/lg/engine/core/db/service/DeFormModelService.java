package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.DeFormModel;

import java.util.List;

public interface DeFormModelService {


    int deleteByPrimaryKey(Long id);

    int insert(DeFormModel record);

    DeFormModel selectByPrimaryKey(Long id);

    int updateByPrimaryKey(DeFormModel record);

    int batchInsert(List<DeFormModel> list);

    List<DeFormModel> selectAllByFormModelNameLike(String likeFormModelName, String likeFormModelKey, String appKey);

    List<Long> selectIdByFormModelKey(String formModelKey);

    DeFormModel selectFirstByModelKey(String modelKey);

    String selectModelKeyByFormModelKey(String formModelKey);

    void delDeFromModel(String key);

}












