package com.chaolifang.controller;

import com.chaolifang.dto.LoginDTO;
import com.chaolifang.dto.TokenVO;
import com.chaolifang.pojo.User;
import com.chaolifang.result.BaseResult;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/logout")
    public BaseResult logout(HttpServletRequest request){
        //从request中取出token
        String token = TokenUtil.getRequestToken(request);
        authService.logout(token);
        return BaseResult.ok();
    }
    @PostMapping("/login")
    public BaseResult login(@RequestBody LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        //用户信息
        User user = authService.findByUsername(username);
        //账号不存在,密码错误
        if(user == null || !user.getPassword().equals(password)){
            return BaseResult.notOk("账户或者密码错误");
        }else{
            //生成token,并保存到数据库
            String token = authService.createToken(user);
            TokenVO tokenVO = new TokenVO();
            tokenVO.setToken(token);
            return BaseResult.ok(token);
        }
    }

    @PostMapping("/test")
    public BaseResult test(String token){
        return BaseResult.ok("恭喜你,验证通过了，我可以返回数据给你");
    }
}
