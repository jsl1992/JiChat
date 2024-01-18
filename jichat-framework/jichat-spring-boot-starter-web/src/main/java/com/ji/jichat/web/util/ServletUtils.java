//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ji.jichat.web.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ServletUtils {
    private ServletUtils() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
    }

    public static void asyncShareRequestAttributes() {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
    }
}
