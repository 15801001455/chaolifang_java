package com.chaolifang.mapper;

import com.chaolifang.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User user);
}
