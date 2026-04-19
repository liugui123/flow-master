package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.HiRuTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface HiRuTaskMapper {
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
    int insert(HiRuTask record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    HiRuTask selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(HiRuTask record);

    int batchInsert(@Param("list") List<HiRuTask> list);

    String selectFirstVarJsonByTaskId(@Param("taskId") Long taskId);

    HiRuTask selectFirstByTaskId(@Param("taskId") Long taskId);

    HiRuTask selectFirstByActinstId(@Param("actinstId") Long actinstId);

    List<HiRuTask> selectByProcinstIdOrderByGmtCreateAsc(@Param("procinstId") Long procinstId);

    Long selectFirstTaskIdByActinstId(@Param("actinstId") Long actinstId);

    Set<Long> selectTaskIdByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    List<HiRuTask> selectByTaskIdIn(@Param("taskIdCollection") Collection<Long> taskIdCollection);

    List<HiRuTask> selectByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);
}