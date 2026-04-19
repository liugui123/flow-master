package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.ReProcess;

import java.util.Collection;
import java.util.List;

public interface ReProcessMapper {
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
    int insert(ReProcess record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ReProcess selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ReProcess record);

    int batchInsert(@Param("list") List<ReProcess> list);

    ReProcess selectFirstByProcKeyOrderByProcVersionDesc(@Param("procKey") String procKey);

    /**
     * 查询已经发布的流程key集合详情内容
     */
    List<ReProcess> selectLastByProcKeyIn(@Param("procKeyCollection") Collection<String> procKeyCollection);

    int updateProcJsonById(@Param("updatedProcJson") String updatedProcJson, @Param("id") Long id);
}