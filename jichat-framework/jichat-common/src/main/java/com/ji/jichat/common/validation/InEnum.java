package com.ji.jichat.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义校验注解 @InEnum，用于验证字段值是否在指定的枚举范围内。
 */
@Target({
        ElementType.METHOD,      // 可以应用于方法
        ElementType.FIELD,       // 可以应用于字段
        ElementType.ANNOTATION_TYPE, // 可以应用于注解类型
        ElementType.CONSTRUCTOR, // 可以应用于构造函数
        ElementType.PARAMETER,   // 可以应用于参数
        ElementType.TYPE_USE     // 可以应用于类型使用
})
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时保留，可以通过反射读取
@Documented // 注解会被包含在 Javadoc 中
@Constraint(
        validatedBy = {InEnumValidator.class} // 指定验证器类
)
public @interface InEnum {

    /**
     * 指定要验证的枚举类。
     *
     * @return 枚举类
     */
    Class<? extends Enum<?>> value();

    /**
     * 默认错误消息，当字段值不在指定的枚举范围内时使用。
     *
     * @return 默认错误消息
     */
    String message() default "必须在指定范围 {value}";

    /**
     * 指定验证组，可以用于分组验证。
     *
     * @return 验证组
     */
    Class<?>[] groups() default {};

    /**
     * 指定负载，可以用于传递额外的信息。
     *
     * @return 负载
     */
    Class<? extends Payload>[] payload() default {};
}