package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.DeFormModelDetailMapper;
import org.lg.engine.core.db.model.DeFormModelDetail;
import org.lg.engine.core.db.service.DeFormModelDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeFormModelDetailServiceImpl implements DeFormModelDetailService {

    @Resource
    private DeFormModelDetailMapper deFormModelDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return deFormModelDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DeFormModelDetail record) {
        return deFormModelDetailMapper.insert(record);
    }

    @Override
    public DeFormModelDetail selectByPrimaryKey(Long id) {
        return deFormModelDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(DeFormModelDetail record) {
        return deFormModelDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<DeFormModelDetail> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return deFormModelDetailMapper.batchInsert(list);
    }

    @Override
    public int deleteByFormModelId(Long formModelId) {
        return deFormModelDetailMapper.deleteByFormModelId(formModelId);
    }

    @Override
    public List<DeFormModelDetail> selectByFormModelId(Long formModelId) {
        return deFormModelDetailMapper.selectByFormModelId(formModelId);
    }

}