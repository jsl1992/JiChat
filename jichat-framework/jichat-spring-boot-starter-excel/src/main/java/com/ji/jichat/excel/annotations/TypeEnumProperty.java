package com.ji.jichat.excel.annotations;

import java.lang.annotation.*;

/**
 * 枚举对象参数注解
 *
 * @author jishenglong on 2019/11/28 16:14
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TypeEnumProperty {

    /**
     * 枚举对象
     **/
    Class value();

}
