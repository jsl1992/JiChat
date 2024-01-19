package com.ji.jichat.web.core.constant;

public interface CommonWebConstants {


    /**
     * HTTP Request Attr - Controller 执行返回
     *
     * 通过该 Request 的 Attribute，获取到请求执行结果，从而在访问日志中，记录是否成功。
     */
    String REQUEST_ATTR_ACCESS_START_TRACE_ID = "JiChat_traceId";
    /**
     * HTTP Request Attr - 访问开始时间
     */
    String REQUEST_ATTR_ACCESS_START_TIME = "JiChat_access_start_time";


}
