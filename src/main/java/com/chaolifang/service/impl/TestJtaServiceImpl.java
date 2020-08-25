package com.chaolifang.service.impl;

import com.chaolifang.dbAop.DataSource;
import com.chaolifang.dbAop.DataSourceNames;
import com.chaolifang.mapper.UserMapper;
import com.chaolifang.pojo.User;
import com.chaolifang.service.TestJtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestJtaServiceImpl implements TestJtaService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void testInsertUser(User user) {
        int insertNum = userMapper.insert(user);
        System.out.println("插入成功,条数:" + insertNum);
    }

    @Override
    @DataSource(DataSourceNames.TWO)
    public void testInsertUser2(User user) {
        int insertNum = userMapper.insert(user);
        System.out.println("插入成功,条数:" + insertNum);

    }
}