package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.DeServiceModel;

import java.util.List;

public interface DeServiceModelMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(DeServiceModel record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(DeServiceModel record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    DeServiceModel selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(DeServiceModel record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DeServiceModel record);

    int batchInsert(@Param("list") List<DeServiceModel> list);

    //    DeServiceModel selectFirstByModelKey(@Param("serviceModelKey") String serviceModelKey);
    DeServiceModel selectFirstByServiceModelKey(@Param("serviceModelKey") String serviceModelKey);

    List<Long> selectFirstIdsByServiceModelKey(String serviceModelKey);
}