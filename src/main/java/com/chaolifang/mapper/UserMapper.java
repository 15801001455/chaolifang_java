package com.chaolifang.mapper;

import com.chaolifang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insert(User user);

    User findByUsername(@Param("username") String username);

    void update(User user);

    User findByToken(@Param("token") String token);
}
