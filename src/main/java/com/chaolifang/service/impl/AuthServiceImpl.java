package com.chaolifang.service.impl;

import com.chaolifang.config.context.CurrentAuth;
import com.chaolifang.config.context.CurrentRole;
import com.chaolifang.mapper.UserMapper;
import com.chaolifang.pojo.Auth;
import com.chaolifang.pojo.Role;
import com.chaolifang.pojo.User;
import com.chaolifang.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    //12小时后失效
    private final static int EXPIRE = 12;

    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public User createToken(User user) {
        //用UUID生成token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        //保存到数据库 update jyc 这里先假设用户名是唯一的
        User byUsername = findByUsername(user.getUsername());
        user.setLoginTime(now);
        user.setExpireTime(expireTime);
        user.setToken(token);
        if(byUsername == null){
            userMapper.insert(user);
        }else{
            userMapper.update(user);
        }
        return user;
    }

    @Override
    public void logout(String token) {
        User user = userMapper.findByToken(token);
        //用UUID生成token
        token = UUID.randomUUID().toString();
        //修改用户的token使原本的token失效，前端需配合将token清除
        user.setToken(token);
        userMapper.update(user);
    }

    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

    @Override
    public Role findRoleByUserId(Integer userId) {
        return userMapper.findCurrentRoleByUserId(userId);
    }

    @Override
    public List<Auth> findAuthByCurrentRole(Integer roleId) {
        return userMapper.findAuthByCurrentRole(roleId);
    }
}
