package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.ReFormStatusEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.DeFormModelDetailMapper;
import org.lg.engine.core.db.mapper.DeFormModelMapper;
import org.lg.engine.core.db.mapper.ReFormModelDetailMapper;
import org.lg.engine.core.db.mapper.ReFormModelMapper;
import org.lg.engine.core.db.model.DeFormModel;
import org.lg.engine.core.db.model.ReFormModel;
import org.lg.engine.core.db.service.DeFormModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeFormModelServiceImpl implements DeFormModelService {

    @Resource
    private DeFormModelMapper deFormModelMapper;
    @Resource
    private DeFormModelDetailMapper deFormModelDetailMapper;
    @Resource
    private ReFormModelMapper reFormModelMapper;
    @Resource
    private ReFormModelDetailMapper reFormModelDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return deFormModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DeFormModel record) {
        return deFormModelMapper.insert(record);
    }

    @Override
    public DeFormModel selectByPrimaryKey(Long id) {
        return deFormModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(DeFormModel record) {
        return deFormModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<DeFormModel> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return deFormModelMapper.batchInsert(list);
    }

    @Override
    public List<DeFormModel> selectAllByFormModelNameLike(String likeFormModelName, String likeFormModelKey, String appKey) {
        return deFormModelMapper.selectByFormModelNameLikeOrAppKeyOrFormModelKey(likeFormModelName, likeFormModelKey, appKey);
    }

    @Override
    public List<Long> selectIdByFormModelKey(String formModelKey) {
        return deFormModelMapper.selectIdByFormModelKey(formModelKey);
    }

    @Override
    public DeFormModel selectFirstByModelKey(String modelKey) {
        return deFormModelMapper.selectFirstByModelKey(modelKey);
    }

    @Override
    public String selectModelKeyByFormModelKey(String formModelKey) {
        return deFormModelMapper.selectModelKeyByFormModelKey(formModelKey);
    }

    @Override
    public void delDeFromModel(String key) {
        DeFormModel deFormModel = selectFirstByModelKey(key);
        Assert.isTrue(
                deFormModel != null,
                WfExceptionCode.DE_FORM_MODEL_EMPTY.getMsg(),
                WfExceptionCode.DE_FORM_MODEL_EMPTY.getCode()
        );

        deleteByPrimaryKey(deFormModel.getId());
        deFormModelDetailMapper.deleteByFormModelId(deFormModel.getId());
        //停用所有发布的版本
        reFormModelMapper.updateStatusByFormModelKey(ReFormStatusEnum.DISABLED.getStatus(),deFormModel.getFormModelKey());
    }
}








