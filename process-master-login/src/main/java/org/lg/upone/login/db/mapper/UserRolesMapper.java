package org.lg.upone.login.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lg.upone.login.db.model.UserRoles;

@Mapper
public interface UserRolesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoles record);

    int insertSelective(UserRoles record);

    UserRoles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoles record);

    int updateByPrimaryKey(UserRoles record);
}