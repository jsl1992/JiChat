package com.ji.jichat.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    private List<Integer> values;

    @SneakyThrows
    @Override
    public void initialize(InEnum annotation) {
        values = new ArrayList<>();
        final Class<? extends Enum<?>> clazz = annotation.value();
        // 获取枚举常量数组
        Enum<?>[] enumConstants = clazz.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            // 获取枚举常量的 code 属性
            Field codeField = enumConstant.getClass().getDeclaredField("code");
            // 设置字段可访问，即使它是私有的
            codeField.setAccessible(true);
            // 获取 code 属性的值
            int code = (int) codeField.get(enumConstant);
            values.add(code);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 为空时，默认不校验，即认为通过
        if (value == null) {
            return true;
        }
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}

