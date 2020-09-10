package com.chaolifang.config.context;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrentUserInfo {
    private Integer id;

    private String username;//用户名
    /**
     * password 密码
     */
    private String password;
    /**
     * token 登陆凭证
     */
    private String token;
    /**
     * token 过期时间
     */
    private LocalDateTime expireTime;
    /**
     *  登录时间
     */
    private LocalDateTime loginTime;
}
