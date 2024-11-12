package com.ji.jichat.excel.util.annotations;

import java.lang.annotation.*;

/**
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KeyValueConverterProperty {

    /**
     * 键值对 键值转换 规则 需去掉{} 分隔符为,;  {key}={value},{key}={value} 或 {key}={value};{key}={value}
     * example: 1=微信,2=支付宝
     *
     * @return
     */
    String value();

}
