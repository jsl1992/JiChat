package com.ji.jichat.web.core.handler;


import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.web.util.CommonWebUtil;
import com.ji.jichat.web.util.HttpContextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 全局响应结果（ResponseBody）处理器
 *
 * @author jisl on 2024/1/29 11:16
 **/
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<CommonResult> {

    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        // 只拦截返回结果为 CommonResult 类型
        return returnType.getMethod().getReturnType() == CommonResult.class;
    }


    @Override
    public CommonResult beforeBodyWrite(CommonResult commonResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //每个返回值，都加入traceId，这样方便定位日志
        commonResult.setTraceId(CommonWebUtil.getTraceId(Objects.requireNonNull(HttpContextUtil.getHttpServletRequest())));
        return commonResult;
    }
}
