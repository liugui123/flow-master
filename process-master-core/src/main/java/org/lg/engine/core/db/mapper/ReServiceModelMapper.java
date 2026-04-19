package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.ReServiceModel;

import java.util.List;

@Mapper
public interface ReServiceModelMapper {
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
    int insert(ReServiceModel record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ReServiceModel record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ReServiceModel selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ReServiceModel record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ReServiceModel record);

    int batchInsert(@Param("list") List<ReServiceModel> list);

    /**
     * 查询应用详情最大的版本
     */
    Integer selectMaxModelVersionByModelKey(@Param("modelKey") String modelKey);

    /**
     * 根据应用和标记查询
     */
    List<ReServiceModel> selectByAppKeyAndFlags(@Param("appKey") String appKey, @Param("flags") Integer flags);

    /**
     * 查询应用下的最新服务信息
     */
    List<ReServiceModel> selectLastByAppKey(@Param("appKey") String appKey);

    List<ReServiceModel> pageByModelKey(@Param("modelKey")String modelKey,
                                     @Param("status")Integer status,
                                     @Param("startRow") int startRow,
                                     @Param("pageSize") Integer pageSize);

    Integer countByModelKey(@Param("modelKey")String modelKey,@Param("status")Integer status);

    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Long id);

    int updateStatusByServiceModelKey(@Param("updatedStatus")Integer updatedStatus,@Param("serviceModelKey")String serviceModelKey);

	
}