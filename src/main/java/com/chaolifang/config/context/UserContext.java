package com.chaolifang.config.context;

import java.util.List;

/**
 * 用户上下文
 *
 * @author zhousongbai
 */
public class UserContext {
    private static ThreadLocal<CurrentUserInfo> currentUser = new ThreadLocal<>();
    private static ThreadLocal<CurrentRole> currentRole = new ThreadLocal<>();//当前正在使用的角色
    private static ThreadLocal<List<CurrentAuth>> currentAuthList = new ThreadLocal<>();//当前角色对应的权限列表

    public static void setUser(CurrentUserInfo userInfo) {
        currentUser.set(userInfo);
    }

    public static CurrentUserInfo getUser() {
        return currentUser.get();
    }

    public static void removeUser() {
        currentUser.remove();
    }


    public static void setCurrentRole(CurrentRole userRole) {
        currentRole.set(userRole);
    }

    public static CurrentRole getCurrentRole() {
        return currentRole.get();
    }

    public static void removeCurrentRole() {
        currentRole.remove();
    }

    public static void setCurrentAuthList(List<CurrentAuth> auth) {
        currentAuthList.set(auth);
    }

    public static List<CurrentAuth> getCurrentAuthList() {
        return currentAuthList.get();
    }

    public static void removeCurrentAuthList() {
        currentAuthList.remove();
    }
}
