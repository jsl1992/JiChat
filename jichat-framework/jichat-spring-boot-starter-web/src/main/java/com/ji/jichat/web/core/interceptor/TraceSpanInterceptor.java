package com.ji.jichat.web.core.interceptor;


import com.ji.jichat.web.core.constant.TraceSpanContext;
import com.ji.jichat.web.util.CommonWebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  TraceId和SpanId拦截器
 */
public class TraceSpanInterceptor extends HandlerInterceptorAdapter {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        TraceSpanContext.storeTraceSpan(request);
        CommonWebUtil.setAccessStartTime(request);
        CommonWebUtil.setTraceId(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TraceSpanContext.removeTraceSpan();
    }


}
