package com.ji.jichat.web.core.constant;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

/**
 * 跟踪标识
 * TRACE_ID（跟踪标识）和 SPAN_ID（跨度标识）是在分布式系统中用于跟踪和监控请求流的标识符
 */
@Slf4j
public class TraceSpanContext {

    public static final String TRACE_ID = "X-B3-TraceId";
    public static final String SPAN_ID = "X-B3-SpanId";

    public static void storeTraceSpan(HttpServletRequest request) {
        String traceId = request.getHeader(TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = createTraceId();
        }
        String spanId = createSpanId();
        MDC.put(TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);
    }


    public static void cleanTraceSpan() {
        try {
            MDC.clear();
        } catch (Exception e) {
            log.error("cleanSpan 错误", e);
        }
    }

    public static String createSpanId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public static String createTraceId() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public static String getTriceId() {
        return MDC.get(TRACE_ID);
    }

    public static String getSpanId() {
        return MDC.get(SPAN_ID);
    }


}
