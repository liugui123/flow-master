package org.lg.engine.core.db.service.impl;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuActinstSiteMapper;
import org.lg.engine.core.db.model.RuActinstSite;
import org.lg.engine.core.db.service.RuActinstSiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class RuActinstSiteServiceImpl implements RuActinstSiteService {

    @Resource
    private RuActinstSiteMapper ruActinstSiteMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruActinstSiteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RuActinstSite record) {
        return ruActinstSiteMapper.insert(record);
    }

    @Override
    public RuActinstSite selectByPrimaryKey(Long id) {
        return ruActinstSiteMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuActinstSite record) {
        return ruActinstSiteMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<RuActinstSite> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return ruActinstSiteMapper.batchInsert(list);
    }

    @Override
    public Set<Long> selectTargetIdByActinstId(Long actinstId) {
        return ruActinstSiteMapper.selectTargetIdByActinstId(actinstId);
    }

    @Override
    public Set<Long> selectActinstIdByTargetId(Long targetId) {
        return ruActinstSiteMapper.selectActinstIdByTargetId(targetId);
    }

    @Override
    public Long selectFirstTargetIdByActinstId(Long actinstId) {
        return ruActinstSiteMapper.selectFirstTargetIdByActinstId(actinstId);
    }

    @Override
    public List<RuActinstSite> selectByProInstId(Long proInstId) {
        return ruActinstSiteMapper.selectByProInstId(proInstId);
    }

    @Override
    public void deleteByProcinstId(Long procinstId) {
        if (procinstId == null) {
            return;
        }
        ruActinstSiteMapper.deleteByProInstId(procinstId);
    }

}