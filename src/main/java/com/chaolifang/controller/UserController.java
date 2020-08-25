package com.chaolifang.controller;

import com.chaolifang.result.BaseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/logout")
    public BaseResult logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseResult.ok();
    }
    @PostMapping("/login")
    public BaseResult login(){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin","admin");
        try {
            subject.login(usernamePasswordToken);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return BaseResult.notOk("账户或者密码错误");
        }catch(AuthorizationException e){
            e.printStackTrace();
            return BaseResult.notOk("没有权限");
        }
        return BaseResult.ok("admin");
    }


}
