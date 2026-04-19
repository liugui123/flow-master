package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.ReFormModel;

import java.util.Collection;
import java.util.List;

public interface ReFormModelMapper {
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
    int insert(ReFormModel record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ReFormModel selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ReFormModel record);

    int batchInsert(@Param("list") List<ReFormModel> list);

    Integer selectFirstVersionByFormModelKeyOrderByVersionDesc(@Param("formModelKey") String formModelKey);

    Integer selectMaxVersionByAppKey(@Param("modelKey") String modelKey);

    ReFormModel selectFirstByFormModelKeyOrderByModelVersionDesc(@Param("formModelKey") String formModelKey);

    /**
     * 查询某个应用下的最大版本的表单
     */
    List<ReFormModel> selectMaxVersionFormByAppKey(@Param("likeFormModelName") String likeFormModelName, @Param("appKey") String appKey);

    /**
     * id批量查询
     */
    List<ReFormModel> selectByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<ReFormModel> pageByModelKey(@Param("modelKey")String modelKey,
                                     @Param("status")Integer status,
                                     @Param("startRow") int startRow,
                                     @Param("pageSize") Integer pageSize);

    Integer countByModelKey(@Param("modelKey")String modelKey,@Param("status")Integer status);

    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Long id);

    int updateStatusByFormModelKey(@Param("updatedStatus")Integer updatedStatus,@Param("formModelKey")String formModelKey);


}