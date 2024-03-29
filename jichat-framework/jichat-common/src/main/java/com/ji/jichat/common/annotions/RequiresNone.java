package com.ji.jichat.common.annotions;

import java.lang.annotation.*;

/**
 * 通过将该注解添加到 Controller 的方法上，声明无需进行登陆
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE}) // 暂时不支持 ElementType.TYPE ，因为没有场景
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresNone {
}
