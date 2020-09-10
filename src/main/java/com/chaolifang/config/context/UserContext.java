package com.chaolifang.config.context;

/**
 * 用户上下文
 *
 * @author zhousongbai
 */
public class UserContext {
    private static ThreadLocal<CurrentUserInfo> currentUserInfoThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<CurrentRole> currentUserRoleThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<CurrentGroup> currentUserGroupThreadLocal = new ThreadLocal<>();

    public static void setUser(CurrentUserInfo userInfo) {
        currentUserInfoThreadLocal.set(userInfo);
    }

    public static CurrentUserInfo getUser() {
        return currentUserInfoThreadLocal.get();
    }

    public static void removeUser() {
        currentUserInfoThreadLocal.remove();
    }


    public static void setUserRole(CurrentRole userRole) {
        currentUserRoleThreadLocal.set(userRole);
    }

    public static CurrentRole getUserRole() {
        return currentUserRoleThreadLocal.get();
    }

    public static void removeUserRole() {
        currentUserRoleThreadLocal.remove();
    }

    public static void setUserGroup(CurrentGroup group) {
        currentUserGroupThreadLocal.set(group);
    }

    public static CurrentGroup getUserGroup() {
        return currentUserGroupThreadLocal.get();
    }

    public static void removeUserGroup() {
        currentUserGroupThreadLocal.remove();
    }
}
