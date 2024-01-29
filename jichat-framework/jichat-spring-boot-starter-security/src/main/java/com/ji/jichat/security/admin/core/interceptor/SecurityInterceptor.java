package com.ji.jichat.security.admin.core.interceptor;


import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.enums.ErrorCodeEnum;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.security.admin.utils.JwtUtil;
import com.ji.jichat.user.api.UserRpc;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.web.core.constant.CommonWebConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class SecurityInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private UserRpc userRpc;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isRequiresAuthenticate((HandlerMethod) handler)) {
            //不需要验证用户直接返回
            return true;
        }
        // 获得访问令牌
        String accessToken = request.getHeader(CommonWebConstants.AUTHORIZATION);
        if (StringUtils.isEmpty(accessToken)) {
            throw new ServiceException(ErrorCodeEnum.UNAUTHORIZED);
        }
        String loginKey = JwtUtil.validateJwtWithGetSubject(accessToken);
        if (StringUtils.isEmpty(loginKey)) {
            throw new ServiceException(ErrorCodeEnum.TOKEN_EXPIRES);
        }
        final LoginUser loginUser = userRpc.getLoginUserByLoginKey(loginKey).getCheckedData();
        if (Objects.isNull(loginUser)) {
            throw new ServiceException(ErrorCodeEnum.UNAUTHORIZED);
        }
        UserContext.set(loginUser);
        return true;
    }

    private boolean isRequiresAuthenticate(HandlerMethod handlerMethod) {
        if (handlerMethod.getBeanType().getAnnotation(RequiresNone.class) != null) {
            //类上有RequiresNone 直接返回 不用进行权限校验
            return false;
        }
        return !handlerMethod.hasMethodAnnotation(RequiresNone.class);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清空 SecurityContext
        UserContext.clear();
    }

}
