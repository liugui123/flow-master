package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.ReFormModelDetailMapper;
import org.lg.engine.core.db.model.ReFormModelDetail;
import org.lg.engine.core.db.service.ReFormModelDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class ReFormModelDetailServiceImpl implements ReFormModelDetailService {

    @Resource
    private ReFormModelDetailMapper reFormModelDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return reFormModelDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReFormModelDetail record) {
        return reFormModelDetailMapper.insert(record);
    }

    @Override
    public ReFormModelDetail selectByPrimaryKey(Long id) {
        return reFormModelDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(ReFormModelDetail record) {
        return reFormModelDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<ReFormModelDetail> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return reFormModelDetailMapper.batchInsert(list);
    }

    @Override
    public List<ReFormModelDetail> selectByFormModelId(Long formModelId) {
        return reFormModelDetailMapper.selectByFormModelId(formModelId);
    }

    @Override
    public List<ReFormModelDetail> selectByFormModelIdIn(Collection<Long> formModelIdCollection) {
        return reFormModelDetailMapper.selectByFormModelIdIn(formModelIdCollection);
    }

    @Override
    public void updateStatusById(Integer updatedStatus, Long id) {
        if (updatedStatus == null || id == null) {
            return;
        }
        reFormModelDetailMapper.updateStatusById(updatedStatus, id);
    }


}