package com.ji.jichat.web.util;


import com.ji.jichat.web.core.constant.CommonWebConstants;
import com.ji.jichat.web.core.constant.TraceSpanContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;


public class CommonWebUtil {


    public static String getTraceId(ServletRequest request) {
        return (String) request.getAttribute(CommonWebConstants.REQUEST_ATTR_ACCESS_START_TRACE_ID);
    }

    public static void setTraceId(HttpServletRequest request) {
        request.setAttribute(CommonWebConstants.REQUEST_ATTR_ACCESS_START_TRACE_ID, TraceSpanContext.getTriceId());
    }


    public static void setAccessStartTime(HttpServletRequest request) {
        request.setAttribute(CommonWebConstants.REQUEST_ATTR_ACCESS_START_TIME, System.currentTimeMillis());
    }

    public static long getAccessStartTime(HttpServletRequest request) {
        return (long) request.getAttribute(CommonWebConstants.REQUEST_ATTR_ACCESS_START_TIME);
    }


}
