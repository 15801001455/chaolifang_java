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
        user.setPassword("10");
        try {
            testJtaService.testInsertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("触发事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return "ok";
    }

    @GetMapping("/testInsertTwo")
    @Transactional
    public String testInsertTwo() throws Exception{
        User user = new User();
        user.setUsername("testUserTwo");
        user.setPassword("10");
        try{
            testJtaService.testInsertUser2(user);
            int num = 1/0;
        }catch (Exception e){
            System.out.println("触发事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return "ok";
    }

    @GetMapping("/testInsert")
    @Transactional
    public String testInsert() throws Exception{
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("10");
        try{
            testJtaService.testInsertUser(user);
            testJtaService.testInsertUser2(user);
            int num = 1/0;  //能够保证不同的库一起回滚
        }catch (Exception e){
            System.out.println("触发事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return "ok";
    }
}
