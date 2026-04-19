package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.AdminAppMapper;
import org.lg.engine.core.db.model.AdminApp;
import org.lg.engine.core.db.service.AdminAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminAppServiceImpl implements AdminAppService {

    @Resource
    private AdminAppMapper adminAppMapper;

    @Override

    public int deleteByPrimaryKey(Long id) {
        return adminAppMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(AdminApp record) {
        return adminAppMapper.insert(record);
    }

    @Override
    public AdminApp selectByPrimaryKey(Long id) {
        return adminAppMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(AdminApp record) {
        return adminAppMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<AdminApp> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return adminAppMapper.batchInsert(list);
    }
}
