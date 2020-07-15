package com.chaolifang.service;

import com.chaolifang.dao.UserMapper;
import com.chaolifang.domain.User;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 这里只是模拟数据库操作 看用户名能否找到用户
     * @param name
     * @return
     */
    public User getUserByName(String name){
        User user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setPassword("123");
        Map<String,User> userMap = new HashMap<>();
        userMap.put("admin",user);
        return userMap.get(name);
    }
}
