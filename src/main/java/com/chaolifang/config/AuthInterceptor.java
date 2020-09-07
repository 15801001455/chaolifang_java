package com.chaolifang.config;

import com.alibaba.fastjson.JSON;
import com.chaolifang.pojo.User;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.HttpContextUtil;
import com.chaolifang.util.Result;
import com.chaolifang.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String token = TokenUtil.getRequestToken(request);
            if (StringUtils.isBlank(token)) {
                //setReturn(response, 400, "用户未登录，请先登录");
                return false;
            }
            //1. 根据token，查询用户信息
            User userEntity = authService.findByToken(token);
            //2. 若用户不存在,
            if (userEntity == null) {
                //setReturn(response, 400, "用户不存在");
                return false;
            }
            //3. token失效
            if (userEntity.getExpireTime().isBefore(LocalDateTime.now())) {
                //setReturn(response, 400, "用户登录凭证已失效，请重新登录");
                return false;
            }
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
