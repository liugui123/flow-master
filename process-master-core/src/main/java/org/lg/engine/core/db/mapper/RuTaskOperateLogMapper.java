package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.request.TaskOpeLogPageRequest;
import org.lg.engine.core.db.model.RuTaskOperateLog;

import java.util.List;

public interface RuTaskOperateLogMapper {

    int deleteByPrimaryKey(Long id);


    int insert(RuTaskOperateLog record);


    RuTaskOperateLog selectByPrimaryKey(Long id);


    int updateByPrimaryKey(RuTaskOperateLog record);

    int batchInsert(@Param("list") List<RuTaskOperateLog> list);

    List<RuTaskOperateLog> selectByTaskId(@Param("taskId") Long taskId);


    Long selectMaxIdByGmtCreateBetweenEqual(@Param("minGmtCreate") Long minGmtCreate, @Param("maxGmtCreate") Long maxGmtCreate);


    Long selectMinIdByGmtCreateBetweenEqual(@Param("minGmtCreate") Long minGmtCreate, @Param("maxGmtCreate") Long maxGmtCreate);


    Integer countByGmtCreate(@Param("minGmtCreate") Long minGmtCreate, @Param("maxGmtCreate") Long maxGmtCreate);

    List<RuTaskOperateLog> page(TaskOpeLogPageRequest request);

    List<RuTaskOperateLog> selectByIdBetweenEqual(@Param("minId") Long minId, @Param("maxId") Long maxId);

}