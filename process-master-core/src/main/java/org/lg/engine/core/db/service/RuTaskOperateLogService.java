package org.lg.engine.core.db.service;

import org.lg.engine.core.client.model.request.TaskOpeLogPageRequest;
import org.lg.engine.core.client.model.response.TaskOpeLogPageResponse;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.db.model.RuTaskOperateLog;

import java.util.List;

public interface RuTaskOperateLogService {

    int insert(RuTaskOperateLog record);

    RuTaskOperateLog selectByPrimaryKey(Long id);

    List<RuTaskOperateLog> selectByTaskId(Long taskId);

    Page<TaskOpeLogPageResponse> page(TaskOpeLogPageRequest request);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(RuTaskOperateLog record);

    int batchInsert(List<RuTaskOperateLog> list);
}













