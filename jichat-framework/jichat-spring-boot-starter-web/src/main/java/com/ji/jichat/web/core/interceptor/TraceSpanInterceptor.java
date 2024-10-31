package com.ji.jichat.web.core.interceptor;


import com.ji.jichat.web.core.constant.TraceSpanContext;
import com.ji.jichat.web.util.CommonWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * TraceId和SpanId拦截器
 *
 * @author jisl on 2024/1/29 11:22
 **/
public class TraceSpanInterceptor implements HandlerInterceptor {


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
