package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.RuTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuTaskService {

    int deleteByPrimaryKey(Long id);

    int insert(RuTask record);

    RuTask selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RuTask record);

    int batchInsert(List<RuTask> list);

    List<RuTask> selectByIdIn(Collection<Long> taskIds);

    int deleteByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTask> selectByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTask> selectByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    Set<Long> selectIdByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    RuTask checkAndGetRuTask(Long taskId);

    RuTask checkAndGetRuTaskFromAll(Long taskId);
    Long selectFirstActinstIdById(Long id);

    Set<Long> selectTaskIdByActinstIdsFromAll(Collection<Long> actinstIds);
}















































