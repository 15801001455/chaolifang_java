package com.chaolifang.controller;

import com.chaolifang.config.context.CurrentAuth;
import com.chaolifang.config.context.CurrentUserInfo;
import com.chaolifang.config.context.UserContext;
import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.pojo.Auth;
import com.chaolifang.pojo.Role;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.service.AuthService;
import com.chaolifang.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/menu")
public class MenuController {

    @Autowired
    private AuthService authService;

    //用户能看到什么菜单 每次请求都取角色列表，权限列表应该是有问题的 影响效率 以后再说吧 现在数据量不大
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public BaseResult list(){
        CurrentUserInfo user = UserContext.getUser();
        // 代表当前的角色 当前在这个系统中只有一个角色 可以做角色切换
        Role currentRole = authService.findRoleByUserId(user.getId());
        if(currentRole == null){
            return BaseResult.notOk("用户没有配置角色信息");
        }
        Integer roleId = currentRole.getId();
        List<Auth> auths = authService.findAuthByCurrentRole(roleId);
        return BaseResult.ok(auths);
    }
}
