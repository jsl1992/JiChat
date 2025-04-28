package com.ji.jichat.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ji.jichat.excel.annotations.TypeEnumProperty;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * excel导出枚举转换
 *
 * @author jishenglong on 2019/11/28 16:14
 **/
@Deprecated
public class TypeEnumConverter implements Converter<Integer> {


    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = getEnumClass(excelContentProperty);
        Method getNameMethod = clazz.getDeclaredMethod("getCode", String.class);
        // 通过 Optional 处理可能的 null 值情况
        final String key = cellData.getStringValue();
        Object nameObj = Optional.ofNullable(getNameMethod.invoke(null, key)).orElse(-1);
        return (Integer) nameObj;
    }

    @Override
    public WriteCellData<String> convertToExcelData(Integer key, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 获取枚举类并调用 getName 方法
        Class<?> clazz = getEnumClass(excelContentProperty);
        // 通过 Optional 处理可能的 null 值情况
        if (clazz.isEnum()) {
            Object[] enumConstants = clazz.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                // 获取 getCode 方法
                Method getCodeMethod = clazz.getMethod("getCode");
                int enumCode = (int) getCodeMethod.invoke(enumConstant);
                if (enumCode == key) {
                    // 获取 getName 方法并返回 name
                    Method getNameMethod = clazz.getMethod("getName");
                    return new WriteCellData<>((String) getNameMethod.invoke(enumConstant));
                }
            }
        }
        return new WriteCellData<>("");
    }

    private Class<?> getEnumClass(ExcelContentProperty excelContentProperty) {
        // 获取字段的 TypeEnumProperty 注解
        final Field field = excelContentProperty.getField();
        TypeEnumProperty typeEnumProperty = field.getAnnotation(TypeEnumProperty.class);
        // 确保注解不为 null，并提供自定义错误消息
        Objects.requireNonNull(typeEnumProperty, "TypeEnumProperty annotation is missing on the field.");
        // 返回注解中定义的枚举类
        return typeEnumProperty.value();
    }

}
