package com.chaolifang.util;


import com.alibaba.druid.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String getRequestToken(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        return token;
    }
}
