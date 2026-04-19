package org.lg.upone.login.db.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.lg.upone.login.db.model.UserRoles;
import org.lg.upone.login.db.mapper.UserRolesMapper;
@Service
public class UserRolesService{

    @Resource
    private UserRolesMapper userRolesMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return userRolesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(UserRoles record) {
        return userRolesMapper.insert(record);
    }

    
    public int insertSelective(UserRoles record) {
        return userRolesMapper.insertSelective(record);
    }

    
    public UserRoles selectByPrimaryKey(Long id) {
        return userRolesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(UserRoles record) {
        return userRolesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(UserRoles record) {
        return userRolesMapper.updateByPrimaryKey(record);
    }

}
