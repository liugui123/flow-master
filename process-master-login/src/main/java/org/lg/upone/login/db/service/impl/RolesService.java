package org.lg.upone.login.db.service.impl;

import org.lg.upone.login.db.model.ERole;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.lg.upone.login.db.mapper.RolesMapper;
import org.lg.upone.login.db.model.Roles;

import java.util.Optional;

@Service
public class RolesService{

    @Resource
    private RolesMapper rolesMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return rolesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Roles record) {
        return rolesMapper.insert(record);
    }

    
    public int insertSelective(Roles record) {
        return rolesMapper.insertSelective(record);
    }

    
    public Roles selectByPrimaryKey(Long id) {
        return rolesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Roles record) {
        return rolesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Roles record) {
        return rolesMapper.updateByPrimaryKey(record);
    }

    public Optional<Roles> findByName(ERole name){
        if (name == null) {
            return Optional.empty();
        }
        Roles role = rolesMapper.findFirstByRoleName(name.name());
        return Optional.ofNullable(role);
    }
}
