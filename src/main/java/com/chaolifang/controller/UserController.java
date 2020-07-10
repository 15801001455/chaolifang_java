package com.chaolifang.controller;

import com.chaolifang.domain.User;
import com.chaolifang.result.BaseResult;
import com.chaolifang.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public BaseResult logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseResult.ok();
    }

    /*@GetMapping("/login")
    public BaseResult login(){
        User user = new User();
        BaseResult login = login(user);
        return login;
    }*/

    @PostMapping("/login")
    public BaseResult login(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(usernamePasswordToken);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return BaseResult.notOk("账户或者密码错误");
        }catch(AuthorizationException e){
            e.printStackTrace();
            return BaseResult.notOk("没有权限");
        }
        return BaseResult.ok(user.getUsername());
    }


}
