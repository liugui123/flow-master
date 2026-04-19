package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.mapper.HiRuProcinstMapper;
import org.lg.engine.core.db.model.HiRuProcinst;
import org.lg.engine.core.db.service.HiRuProcinstService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HiRuProcinstServiceImpl implements HiRuProcinstService {

    @Resource
    private HiRuProcinstMapper hiRuProcinstMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return hiRuProcinstMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HiRuProcinst record) {
        if (CommandContextFactory.getCommandContext().getRuTaskUser() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuTaskUser().getAppKey());
        } else if (CommandContextFactory.getCommandContext().getRuProcinst() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuProcinst().getAppKey());
        }
        return hiRuProcinstMapper.insert(record);
    }

    @Override
    public HiRuProcinst selectByPrimaryKey(Long id) {
        return hiRuProcinstMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(HiRuProcinst record) {
        return hiRuProcinstMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<HiRuProcinst> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return hiRuProcinstMapper.batchInsert(list);
    }

    @Override
    public HiRuProcinst selectByProcInstId(Long procinstId) {
        return hiRuProcinstMapper.selectByProcInstId(procinstId);
    }

    @Override
    public int updateBatch(List<HiRuProcinst> list) {
        return hiRuProcinstMapper.updateBatch(list);
    }

}






