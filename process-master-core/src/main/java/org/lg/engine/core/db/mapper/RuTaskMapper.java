package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.RuTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuTaskMapper {
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
    int insert(RuTask record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    RuTask selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RuTask record);

    int batchInsert(@Param("list") List<RuTask> list);

    List<RuTask> selectByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<Long> selectIdByActinstId(@Param("actinstId") Long actinstId);

    int deleteByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTask> selectByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTask> selectRuTaskByActInstId(@Param("actinstId") Long actinstId);

    int deleteByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    RuTask selectFirstByActinstId(@Param("actinstId") Long actinstId);

    List<RuTask> selectByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    Set<Long> selectIdByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    List<RuTask> selectByProcinstIdOrderByGmtCreateAsc(@Param("procinstId") Long procinstId);

    Long selectFirstActinstIdById(@Param("id") Long id);

    String selectVarJsonById(@Param("id") Long id);


}