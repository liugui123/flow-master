package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.enumerate.RuTaskStatusEnum;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.HiRuTaskMapper;
import org.lg.engine.core.db.model.HiRuTask;
import org.lg.engine.core.db.service.HiRuTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class HiRuTaskServiceImpl implements HiRuTaskService {

    @Resource
    private HiRuTaskMapper hiRuTaskMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return hiRuTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HiRuTask record) {
        record.setStatus(RuTaskStatusEnum.DONE.getStatus());
        return hiRuTaskMapper.insert(record);
    }

    @Override
    public HiRuTask selectByPrimaryKey(Long id) {
        return hiRuTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(HiRuTask record) {
        return hiRuTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<HiRuTask> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        for (HiRuTask hiRuTask : list) {
            hiRuTask.setStatus(RuTaskStatusEnum.DONE.getStatus());
        }
        return hiRuTaskMapper.batchInsert(list);
    }

    @Override
    public HiRuTask selectFirstByTaskId(Long taskId) {
        return hiRuTaskMapper.selectFirstByTaskId(taskId);
    }

    @Override
    public HiRuTask selectFirstByActinstId(Long actinstId) {
        return hiRuTaskMapper.selectFirstByActinstId(actinstId);
    }

    @Override
    public List<HiRuTask> selectByProcinstIdOrderByGmtCreateAsc(Long procinstId) {
        return hiRuTaskMapper.selectByProcinstIdOrderByGmtCreateAsc(procinstId);
    }

    @Override
    public Long selectFirstTaskIdByActinstId(Long actinstId) {
        return hiRuTaskMapper.selectFirstTaskIdByActinstId(actinstId);
    }

    @Override
    public Set<Long> selectTaskIdByActinstIdIn(Collection<Long> actinstIdCollection) {
        return hiRuTaskMapper.selectTaskIdByActinstIdIn(actinstIdCollection);
    }

    @Override
    public List<HiRuTask> selectByTaskIdIn(Collection<Long> idCollection) {
        return hiRuTaskMapper.selectByTaskIdIn(idCollection);
    }

    @Override
    public List<HiRuTask> selectByActinstIdIn(Collection<Long> actinstIdCollection) {
        return hiRuTaskMapper.selectByActinstIdIn(actinstIdCollection);
    }

    @Override
    public String selectFirstVarJsonByTaskId(Long taskId) {
        return hiRuTaskMapper.selectFirstVarJsonByTaskId(taskId);
    }
}
