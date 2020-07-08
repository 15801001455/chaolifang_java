package com.chaolifang.shiro.realms;

import com.chaolifang.domain.User;
import com.chaolifang.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 验证用户名是否为空
        Object principal = token.getPrincipal();
        if(principal == null){
            return null;
        }
        // 获取用户信息
        String username = principal.toString();
        User user = userService.getUserByName(username);
        if(user == null){
            // 用户不存在
            return null;
        }else{
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,user.getPassword().toString(),getName());
            return simpleAuthenticationInfo;
        }
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 目前不做权限和角色的认证 先不写
        return null;
    }


}
