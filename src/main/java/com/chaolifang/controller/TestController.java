package com.chaolifang.controller;

import com.chaolifang.pojo.User;
import com.chaolifang.service.TestJtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private TestJtaService testJtaService;

    @GetMapping("/testInsertOne")
    @Transactional
    public String testInsertOne() throws Exception {
        User user = new User();
        user.setUsername("testUserOne");
        user.setAge(10);
        try {
            testJtaService.testInsertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("触发事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return "ok";
    }
}
