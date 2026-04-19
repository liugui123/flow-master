package org.lg.engine.core.db.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.model.request.TaskOpeLogPageRequest;
import org.lg.engine.core.client.model.response.TaskOpeLogPageResponse;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuTaskOperateLogMapper;
import org.lg.engine.core.db.model.RuTaskOperateLog;
import org.lg.engine.core.db.service.RuTaskOperateLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class RuTaskOperateLogServiceImpl implements RuTaskOperateLogService {

    @Resource
    private RuTaskOperateLogMapper ruTaskOperateLogMapper;

    @Override
    public int insert(RuTaskOperateLog record) {
        return ruTaskOperateLogMapper.insert(record);
    }

    @Override
    public RuTaskOperateLog selectByPrimaryKey(Long id) {
        return ruTaskOperateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RuTaskOperateLog> selectByTaskId(Long taskId) {
        return ruTaskOperateLogMapper.selectByTaskId(taskId);
    }

    @Override
    public Page<TaskOpeLogPageResponse> page(TaskOpeLogPageRequest request) {
        Page<TaskOpeLogPageResponse> res = new Page<>();
        request.setStartRow(request.getStartRow());
        request.setEndRow(request.getEndRow() + 1);
        Integer total = ruTaskOperateLogMapper.countByGmtCreate(request.getMinGmtCreate(), request.getMaxGmtCreate());
        res.setTotal(total);
        List<RuTaskOperateLog> ruTaskOperateLogs = ruTaskOperateLogMapper.page(request);
        List<TaskOpeLogPageResponse> resList = Convert.INSTANCE.ruTaskOperateLogsToTaskOpeLogPageResponses(ruTaskOperateLogs);
        res.setRows(resList);
        return res;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruTaskOperateLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuTaskOperateLog record) {
        return ruTaskOperateLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<RuTaskOperateLog> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return ruTaskOperateLogMapper.batchInsert(list);
    }
}