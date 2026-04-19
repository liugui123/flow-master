package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuTaskMapper;
import org.lg.engine.core.db.model.HiRuTask;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.service.HiRuTaskService;
import org.lg.engine.core.db.service.RuTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RuTaskServiceImpl implements RuTaskService {

    @Resource
    private RuTaskMapper ruTaskMapper;

    @Resource
    private HiRuTaskService hiRuTaskService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RuTask record) {
        return ruTaskMapper.insert(record);
    }


    @Override
    public RuTask selectByPrimaryKey(Long id) {
        return ruTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuTask record) {
        return ruTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<RuTask> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return ruTaskMapper.batchInsert(list);
    }

    @Override
    public List<RuTask> selectByIdIn(Collection<Long> taskIds) {
        return ruTaskMapper.selectByIdIn(taskIds);
    }

    @Override
    public int deleteByProcInstId(Long procinstId) {
        return ruTaskMapper.deleteByProcInstId(procinstId);
    }

    @Override
    public List<RuTask> selectByProcInstId(Long procinstId) {
        return ruTaskMapper.selectByProcInstId(procinstId);
    }


    @Override
    public List<RuTask> selectByActinstIdIn(Collection<Long> actinstIdCollection) {
        return ruTaskMapper.selectByActinstIdIn(actinstIdCollection);
    }

    @Override
    public Set<Long> selectIdByActinstIdIn(Collection<Long> actinstIdCollection) {
        if (Utils.isEmpty(actinstIdCollection)) {
            return new HashSet<>(0);
        }
        return ruTaskMapper.selectIdByActinstIdIn(actinstIdCollection);
    }


    @Override
    public RuTask checkAndGetRuTask(Long taskId) {
        RuTask ruTask = ruTaskMapper.selectByPrimaryKey(taskId);
        Assert.isTrue(ruTask != null,
                WfExceptionCode.TASK_BLANK.getMsg(),
                WfExceptionCode.TASK_BLANK.getCode()
        );
        return ruTask;
    }

    @Override
    public RuTask checkAndGetRuTaskFromAll(Long taskId) {
        RuTask ruTask = ruTaskMapper.selectByPrimaryKey(taskId);
        if (ruTask == null) {
            HiRuTask hiRuTask = hiRuTaskService.selectFirstByTaskId(taskId);
            if (hiRuTask != null) {
                ruTask = Convert.INSTANCE.hiRuTaskToRuTask(hiRuTask);
                ruTask.setId(hiRuTask.getTaskId());
            }
        }
        Assert.isTrue(ruTask != null,
                WfExceptionCode.TASK_BLANK.getMsg(),
                WfExceptionCode.TASK_BLANK.getCode()
        );
        return ruTask;
    }


    @Override
    public Long selectFirstActinstIdById(Long id) {
        return ruTaskMapper.selectFirstActinstIdById(id);
    }

    @Override
    public Set<Long> selectTaskIdByActinstIdsFromAll(Collection<Long> actinstIds) {
        Set<Long> res = new HashSet<>(16);
        res.addAll(ruTaskMapper.selectIdByActinstIdIn(actinstIds));
        res.addAll(hiRuTaskService.selectTaskIdByActinstIdIn(actinstIds));
        return res;
    }
}