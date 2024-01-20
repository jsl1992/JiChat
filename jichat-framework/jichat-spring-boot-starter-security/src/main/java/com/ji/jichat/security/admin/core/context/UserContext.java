package com.ji.jichat.security.admin.core.context;


import com.ji.jichat.user.api.vo.LoginUser;

/**
 *
 */
public class UserContext {

    private static final ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();

    public static void set(LoginUser dto) {
        userThreadLocal.set(dto);
    }

    public static LoginUser get() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }

    public static String getUsername() {
        return get() == null ? "未获取到用户名" : get().getUsername();
    }

    public static String getLoginKey() {
        return  get().getLoginKey();
    }

    public static Long getUserId() {
        return get() == null ? -100L : get().getUserId();
    }


}
