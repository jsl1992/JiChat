package com.ji.jichat.common.validation;

import cn.hutool.core.util.StrUtil;
import com.ji.jichat.common.util.RegexUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义手机号校验器，用于验证手机号格式是否正确。
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile annotation) {
        // 初始化方法，可以在这里进行一些初始化操作
        // 由于目前没有需要初始化的操作，所以此方法为空
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }

        // 使用正则表达式校验手机号格式
        return RegexUtils.isMobileSimple(value);
    }
}