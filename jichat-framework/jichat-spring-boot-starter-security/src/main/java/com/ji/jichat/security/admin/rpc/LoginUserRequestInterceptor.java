package com.ji.jichat.security.admin.rpc;


import com.ji.jichat.web.util.HttpContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * LoginUser 的 RequestInterceptor 实现类：Feign 请求时，将 {@link LoginUser} 设置到 header 中，继续透传给被调用的服务
 *
 * @author 芋道源码
 */
public class LoginUserRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        {
            HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
            if (Objects.nonNull(httpServletRequest)) {
                String authentication = httpServletRequest.getHeader("Authorization");
                if (Objects.nonNull(authentication)) {
                    requestTemplate.header("Authorization", authentication);
                }
            }
        }
    }

}
