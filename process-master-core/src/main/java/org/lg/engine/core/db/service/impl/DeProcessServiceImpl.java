package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.DeProcessMapper;
import org.lg.engine.core.db.mapper.RuProcinstMapper;
import org.lg.engine.core.db.model.DeProcess;
import org.lg.engine.core.db.service.DeProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeProcessServiceImpl implements DeProcessService {

    @Resource
    private DeProcessMapper deProcessMapper;
    @Resource
    private RuProcinstMapper ruProcinstMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return deProcessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DeProcess selectByPrimaryKey(Long id) {
        return deProcessMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(DeProcess record) {
        return deProcessMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<DeProcess> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return deProcessMapper.batchInsert(list);
    }

    @Override
    public DeProcess selectFirstByProcKey(String procKey) {
        return deProcessMapper.selectFirstByProcKey(procKey);
    }
}

