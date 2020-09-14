package com.chaolifang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*List<String> patterns = new ArrayList<>();
        patterns.add("/webjars/**");
        patterns.add("/druid/**");
        patterns.add("/sys/login");
        patterns.add("/swagger/**");
        patterns.add("/v2/api-docs");
        patterns.add("/swagger-ui.html");
        patterns.add("/swagger-resources/**");
        patterns.add("/api/user/login");
        patterns.add("/configuration/ui");//这个是swagger打不开在network打开看到的请求地址
        patterns.add("/configuration/security");//这个是swagger打不开在network打开看到的请求地址*/
        //registry.addInterceptor(authInterceptor).excludePathPatterns(patterns).addPathPatterns("/**"); 这样好像拦截不住,下面的拦截方式是成功的

        List<String> patterns = new ArrayList<>();//需要拦截的请求
        //patterns.add("/api/user/test");
        patterns.add("/api/book/**");
        patterns.add("/api/menu/**");
        registry.addInterceptor(authInterceptor).addPathPatterns(patterns);
    }


}
