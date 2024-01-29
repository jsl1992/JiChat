package com.ji.jichat.security.admin.core.context;


import com.ji.jichat.user.api.vo.LoginUser;

/**
 * 登录用户
 *
 * @author jisl on 2024/1/29 11:03
 **/
public class UserContext {

    private static final ThreadLocal<LoginUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(LoginUser dto) {
        USER_THREAD_LOCAL.set(dto);
    }

    public static LoginUser get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }

    public static String getUsername() {
        return get() == null ? "未获取到用户名" : get().getUsername();
    }

    public static String getLoginKey() {
        return get().getLoginKey();
    }

    public static Long getUserId() {
        return get() == null ? -1L : get().getUserId();
    }


}
