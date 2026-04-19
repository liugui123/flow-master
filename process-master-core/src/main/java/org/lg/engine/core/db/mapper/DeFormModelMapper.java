package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.DeFormModel;

import java.util.List;

public interface DeFormModelMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    int deleteByKey(String key);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(DeFormModel record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    DeFormModel selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DeFormModel record);

    int batchInsert(@Param("list") List<DeFormModel> list);

    List<DeFormModel> selectByFormModelNameLikeOrAppKeyOrFormModelKey(
            @Param("likeFormModelName") String likeFormModelName,
            @Param("likeFormModelKey") String formModelKey,
            @Param("appKey") String appKey
    );


    List<Long> selectIdByFormModelKey(@Param("formModelKey") String formModelKey);

    DeFormModel selectFirstByModelKey(@Param("modelKey") String modelKey);

    String selectModelKeyByFormModelKey(@Param("formModelKey") String formModelKey);

    List<DeFormModel> getByIds(List<String> procFormKey);
}