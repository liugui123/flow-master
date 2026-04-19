package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.HiRuTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface HiRuTaskService {


    int deleteByPrimaryKey(Long id);

    int insert(HiRuTask record);

    HiRuTask selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HiRuTask record);

    int batchInsert(List<HiRuTask> list);


    HiRuTask selectFirstByTaskId(Long taskId);


    HiRuTask selectFirstByActinstId(Long actinstId);


    List<HiRuTask> selectByProcinstIdOrderByGmtCreateAsc(Long procinstId);


    Long selectFirstTaskIdByActinstId(Long actinstId);

    Set<Long> selectTaskIdByActinstIdIn(Collection<Long> actinstIdCollection);

    List<HiRuTask> selectByTaskIdIn(Collection<Long> idCollection);

    List<HiRuTask> selectByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    String selectFirstVarJsonByTaskId(Long taskId);
}














































