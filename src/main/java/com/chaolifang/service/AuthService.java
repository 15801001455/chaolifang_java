package com.chaolifang.service;

import com.chaolifang.pojo.User;

public interface AuthService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 为user生成token
     * @param user
     * @return
     */
    User createToken(User user);

    /**
     * 根据token去修改用户token ，使原本token失效
     * @param token
     */
    void logout(String token);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    User findByToken(String token);
}
