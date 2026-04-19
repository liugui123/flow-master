package org.lg.upone.login.db.service.impl;

import org.lg.engine.core.client.utils.Utils;
import org.lg.upone.login.db.mapper.UserMapper;
import org.lg.upone.login.db.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public int deleteByPrimaryKey(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public int insert(User record) {
        return userMapper.insert(record);
    }


    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }


    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }


    public Optional<User> findByUsername(String username) {
        if (Utils.isEmpty(username)) {
            return Optional.empty();
        }
        User user = userMapper.findFirstByUsername(username);
        return Optional.ofNullable(user);
    }

    public Boolean existsByUsername(String username) {
        User user = userMapper.findFirstByUsername(username);
        return user != null;
    }

    public Boolean existsByEmail(String email) {
        Long firstIdByEmail = userMapper.findFirstIdByEmail(email);
        return firstIdByEmail != null;
    }

}
