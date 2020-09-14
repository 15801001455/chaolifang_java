package com.chaolifang.mapper;

import com.chaolifang.config.context.CurrentAuth;
import com.chaolifang.config.context.CurrentRole;
import com.chaolifang.pojo.Auth;
import com.chaolifang.pojo.Role;
import com.chaolifang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);

    User findByUsername(@Param("username") String username);

    void update(User user);

    User findByToken(@Param("token") String token);

    //用户的所有角色列表
    List<Role> findAllRolesByUserId(@Param("userId") Integer userId);

    //用户当前登录的角色 先不用
    Role findCurrentRoleByUserId(@Param("userId") Integer userId);

    List<Auth> findAuthByCurrentRole(@Param("roleId") Integer roleId);
}
