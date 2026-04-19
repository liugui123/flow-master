package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.model.request.ReFormModelPageRequest;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.ReFormModelMapper;
import org.lg.engine.core.db.model.ReFormModel;
import org.lg.engine.core.db.service.ReFormModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReFormModelServiceImpl implements ReFormModelService {

    @Resource
    private ReFormModelMapper reFormModelMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return reFormModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReFormModel record) {
        return reFormModelMapper.insert(record);
    }

    @Override
    public ReFormModel selectByPrimaryKey(Long id) {
        return reFormModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(ReFormModel record) {
        return reFormModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<ReFormModel> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return reFormModelMapper.batchInsert(list);
    }

    @Override
    public Map<Long, ReFormModel> getIdAndFormModelRel(List<Long> reModelIds) {
        if (Utils.isEmpty(reModelIds)) {
            return new HashMap<>();
        }
        List<ReFormModel> reFormModels = reFormModelMapper.selectByIdIn(reModelIds);
        return reFormModels.stream().collect(Collectors.toMap(ReFormModel::getId, Function.identity(), (x, y) -> y));
    }

    public Page<ReFormModel> page(ReFormModelPageRequest request) {
        String modelKey = request.getModelKey();
        Integer status = request.getStatus();
        if (Utils.isEmpty(modelKey) || status == null) {
            return new Page<>();
        }
        Page<ReFormModel> res = new Page<ReFormModel>();
        List<ReFormModel> reFormModels = reFormModelMapper.pageByModelKey(
                modelKey,
                status,
                request.getStartRow(),
                request.getPageSize());

        if (Utils.isEmpty(reFormModels)) {
            return new Page<>();
        }
        res.setRows(reFormModels);
        Integer total = reFormModelMapper.countByModelKey(request.getModelKey(), status);
        res.setTotal(total);
        return res;
    }

    @Override
    public List<ReFormModel> selectMaxVersionFormByAppKey(String formModelName, String appKey) {
        return reFormModelMapper.selectMaxVersionFormByAppKey(formModelName, appKey);
    }

    @Override
    public void disableOrEnable(Integer status,Long id) {
        if (status == null) {
            return;
        }
        if (id == null) {
            return;
        }
        reFormModelMapper.updateStatusById(status,id);
    }

    @Override
    public Integer selectFirstModelVersionByModelKeyOrderByModelVersionDesc(String modelKey) {
        return reFormModelMapper.selectMaxVersionByAppKey(modelKey);
    }

    @Override
    public ReFormModel selectFirstByFormModelKeyOrderByModelVersionDesc(String formModelKey) {
        return reFormModelMapper.selectFirstByFormModelKeyOrderByModelVersionDesc(formModelKey);
    }

}





