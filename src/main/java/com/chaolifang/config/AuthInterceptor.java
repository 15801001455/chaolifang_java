package com.chaolifang.config;

import com.alibaba.fastjson.JSON;
import com.chaolifang.config.context.CurrentAuth;
import com.chaolifang.config.context.CurrentRole;
import com.chaolifang.config.context.CurrentUserInfo;
import com.chaolifang.config.context.UserContext;
import com.chaolifang.pojo.Role;
import com.chaolifang.pojo.User;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.HttpContextUtil;
import com.chaolifang.util.Result;
import com.chaolifang.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 这里很关键 每个完整的请求链(请求接口)结束后，要把当前线程的用户信息移除map，否则内存溢出
        UserContext.removeUser();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String token = TokenUtil.getRequestToken(request);
            if (StringUtils.isBlank(token)) {
                //setReturn(response, 400, "用户未登录，请先登录");
                return false;
            }
            //1. 根据token，查询用户信息
            User user = authService.findByToken(token);
            //2. 若用户不存在,
            if (user == null) {
                //setReturn(response, 400, "用户不存在");
                return false;
            }
            //3. token失效
            if (user.getExpireTime().isBefore(LocalDateTime.now())) {
                //setReturn(response, 400, "用户登录凭证已失效，请重新登录");
                return false;
            }
            //4 用户没有角色应该也不能登录系统
            Role roleByUserId = authService.findRoleByUserId(user.getId());
            if(roleByUserId == null){
                return  false;
            }
            CurrentUserInfo info = new CurrentUserInfo();
            BeanUtils.copyProperties(user,info);
            //这里在当前线程里设置用户的信息，是为了以后使用方便,这个拦截器每个请求能进来的都会设置上User信息,前提是请求需要登录操作才会进入这个拦截器才会设置上上下文信息
            UserContext.setUser(info);
            //当前线程设置用户的角色信息
            CurrentRole role = new CurrentRole();
            UserContext.setCurrentRole(role);
        }
        return true;
    }

    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setStatus(400);
        response.setContentType("application/json;charset=utf-8");
        Result build = Result.build(status, msg);
        String json = JSON.toJSONString(build);
        httpResponse.getWriter().print(json);
    }
}
