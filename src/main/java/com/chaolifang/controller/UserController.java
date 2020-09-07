package com.chaolifang.controller;

import com.chaolifang.dto.LoginDTO;
import com.chaolifang.dto.TokenVO;
import com.chaolifang.pojo.User;
import com.chaolifang.result.BaseResult;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
            User token = authService.createToken(user);
            //TokenVO tokenVO = new TokenVO();
            //tokenVO.setToken(token.getToken());
            //tokenVO.setExpireTime(token.getExpireTime());
            return BaseResult.ok(token.getToken());
        }
    }

    /**
     * 这个方法真的是无奈之举,如果不提前调用接口验证登录信息的话，通过拦截器验证的话，会造成返回接口调用信息400，还没有什么好的方法解决
     * @param token
     * @return
     */
    @GetMapping("/validateToken")
    public BaseResult validateToken(@RequestParam("mytoken") String token){
        if (StringUtils.isBlank(token)) {
            return BaseResult.notOk("用户未登录，请先登录");
        }
        //1. 根据token，查询用户信息
        User userEntity = authService.findByToken(token);
        //2. 若用户不存在,
        if (userEntity == null) {
            return BaseResult.notOk("用户不存在");
        }
        //3. token失效
        if (userEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            return BaseResult.notOk("用户登录凭证已失效，请重新登录");
        }
        return BaseResult.ok();
    }

    @GetMapping("/test")
    public BaseResult test(@RequestParam("mytoken") String token){
        return BaseResult.ok("恭喜你,验证通过了，我可以返回数据给你");
    }
}
