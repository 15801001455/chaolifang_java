package com.chaolifang.controller;

import com.chaolifang.config.context.CurrentRole;
import com.chaolifang.dto.LoginDTO;
import com.chaolifang.pojo.Role;
import com.chaolifang.pojo.User;
import com.chaolifang.result.BaseResult;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.EncryptedUtil;
import com.chaolifang.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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
        if(user == null || !user.getPassword().equals(EncryptedUtil.generate(password,username))){//username就d当成盐值用了
            return BaseResult.notOk("账户或者密码错误");
        }else{
            //生成token,并保存到数据库
            User token = authService.createToken(user);
            Integer userId = user.getId();
            Role currentRole = authService.findRoleByUserId(userId);
            if(currentRole == null){
                return BaseResult.notOk("用户无角色,无法登录系统");
            }
            return BaseResult.ok(token.getToken());
        }
    }

    /**
     * 这个方法真的是无奈之举,如果不提前调用接口验证登录信息的话，通过拦截器验证的话，会造成返回接口调用信息400，还没有什么好的方法解决
     * @param token
     * @return
     */
    /*@GetMapping("/validateToken")
    public BaseResult validateToken(@RequestParam("mytoken") String token){
        if (StringUtils.isBlank(token)) {
            return BaseResult.notOk("用户未登录，请先登录");
        }
        //1. 根据token，查询用户信息
        User user = authService.findByToken(token);
        //2. 若用户不存在,
        if (user == null) {
            return BaseResult.notOk("用户不存在");
        }
        //3. token失效
        if (user.getExpireTime().isBefore(LocalDateTime.now())) {
            return BaseResult.notOk("用户登录凭证已失效，请重新登录");
        }
        //4 用户没有角色应该也不能登录系统
        Role roleByUserId = authService.findRoleByUserId(user.getId());
        if(roleByUserId == null){
            return BaseResult.notOk("用户没有角色信息，请联系管理员");
        }
        return BaseResult.ok();
    }*/

    @GetMapping("/test")
    public BaseResult test(@RequestParam("mytoken") String token){
        return BaseResult.ok("恭喜你,验证通过了，我可以返回数据给你");
    }
}
