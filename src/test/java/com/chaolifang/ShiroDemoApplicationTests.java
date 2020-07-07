package com.chaolifang;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Ctrl+H 查看一个类的继承关系
 * Alt+7 查看类中的方法(单元测试比较好用,能一次把一个类中的所有方法列举出来)
 */
@SpringBootTest
class ShiroDemoApplicationTests {

    /**
     * shiro.ini中只有
     * [users]
     * zhang=123
     * wang=123
     */
    @Test
    void testHelloworld() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang1", "123");
        try {
            subject.login(token);
        }catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(true,subject.isAuthenticated());

        subject.logout();
    }

    private void login(String configFile){
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            System.out.println("进入自己捕获的异常 start");
            e.printStackTrace();
            System.out.println("进入自己捕获的异常 end");
        }
    }

    @Test
    void testCustomerRealm() {
        login("classpath:shiro-realm.ini");
    }

}
