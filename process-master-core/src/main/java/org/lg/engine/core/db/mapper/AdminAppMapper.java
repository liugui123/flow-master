package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.AdminApp;

import java.util.List;

public interface AdminAppMapper {
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
    int insert(AdminApp record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    AdminApp selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AdminApp record);

    int batchInsert(@Param("list") List<AdminApp> list);

    List<AdminApp> selectByCreatorIdAndCreatorOrgId(@Param("creatorId") String creatorId, @Param("creatorOrgId") String creatorOrgId);

    /**
     * 查询key对应的id
     *
     * @param appKey 应用key
     * @return id
     */
    Long selectFirstIdByAppKey(@Param("appKey") String appKey);

    /**
     * 查询key对应的id,name
     *
     * @param appKey
     * @return
     */
    AdminApp selectFirstByAppKey(@Param("appKey") String appKey);

    int updateSortScore(@Param("id") Long id, @Param("sortScore") Integer sortScore, @Param("gmtModified") Long gmtModified);

}