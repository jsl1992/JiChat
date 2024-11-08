package com.ji.jichat.web.core.aspect;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.alibaba.fastjson2.JSON;
import com.ji.jichat.web.util.CommonWebUtil;
import com.ji.jichat.web.util.HttpContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 统一日志处理
 *
 * @author jisl on 2023/6/7 17:30
 **/
@Order(1)
@Aspect
@Component
@Slf4j
public class AccessLogAspect {


    public static final Long LONG_TIME = 3000L;

    public static final String GET_LOGIN_USER_METHOD = "com.ji.jichat.user.controller.UserController.getLoginUserByLoginKey";

    @Pointcut("execution(* com.ji.jichat..controller..*.*(..))")
    public void point() {
    }


    @Before("point()")
    public void before(JoinPoint join) {
        // 方法路径
        String methodName = join.getTarget().getClass().getName() + "." + join.getSignature().getName();
        if (methodName.equals(GET_LOGIN_USER_METHOD)) {
            return;
        }
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        final String paramStr = isJsonRequest(request) ? JSON.toJSONString(join.getArgs()) : Arrays.toString(join.getArgs());
        log.info("请求路径:[{}],请求IP:[{}],参数:{}", request.getRequestURL().toString(), JakartaServletUtil.getClientIP(request), paramStr);
    }

    @AfterReturning(pointcut = "point()")
    public void afterReturn() {
        final long timer = System.currentTimeMillis() - CommonWebUtil.getAccessStartTime(HttpContextUtil.getHttpServletRequest());
        if (timer > LONG_TIME) {
            log.warn("异常耗时: {} ms", timer);
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

}
