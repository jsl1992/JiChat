package com.ji.jichat.web.core.interceptor;


import com.alibaba.fastjson.JSON;
import com.ji.jichat.web.core.constant.TraceSpanContext;
import com.ji.jichat.web.util.CommonWebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * 访问日志拦截器
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

//    @Reference(version = "${dubbo.consumer.SystemAccessLogRpc.version}")
//    private SystemAccessLogRpc systemAccessLogRpc;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录当前时间
//        final HttpHeaders headers = request.getHeaders();
//        headers.add(TraceSpanContext.TRACE_ID, TraceSpanContext.getTriceId());
//        headers.add(TraceSpanContext.SPAN_ID, TraceSpanContext.getSpanId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        SystemAccessLogCreateDTO accessLog = new SystemAccessLogCreateDTO();
//        try {
//            // 初始化 accessLog
//            initAccessLog(accessLog, request);
//            // 执行插入 accessLog
////            addAccessLog(accessLog);
//            logger.info("请求入参:{}", JSON.toJSONString(accessLog));
//
//            // TODO 提升：暂时不考虑 ELK 的方案。而是基于 MySQL 存储。如果访问日志比较多，需要定期归档。
//        } catch (Throwable th) {
//            logger.error("[afterCompletion][插入访问日志({}) 发生异常({})", JSON.toJSONString(accessLog), ExceptionUtils.getRootCauseMessage(th));
//        }
        threadLocal.remove();
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
