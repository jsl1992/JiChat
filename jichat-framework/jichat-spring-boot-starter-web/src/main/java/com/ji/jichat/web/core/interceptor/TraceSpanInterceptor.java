package com.ji.jichat.web.core.interceptor;


import com.ji.jichat.web.core.constant.TraceSpanContext;
import com.ji.jichat.web.util.CommonWebUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TraceId和SpanId拦截器
 *
 * @author jisl on 2024/1/29 11:22
 **/
public class TraceSpanInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        TraceSpanContext.storeTraceSpan(request);
        CommonWebUtil.setAccessStartTime(request);
        CommonWebUtil.setTraceId(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        TraceSpanContext.removeTraceSpan();
    }


}
