package com.ji.jichat.excel.util.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ji.jichat.excel.util.annotations.TypeEnumProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * excel导出枚举转换
 *
 * @author jishenglong on 2019/11/28 16:14
 **/
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
        Method getNameMethod = clazz.getDeclaredMethod("getName", Integer.class);
        // 通过 Optional 处理可能的 null 值情况
        Object nameObj = Optional.ofNullable(getNameMethod.invoke(null, key)).orElse(key.toString());
        return new WriteCellData<>(nameObj.toString());
    }

    private Class<?> getEnumClass(ExcelContentProperty excelContentProperty) {
        // 获取字段的 TypeEnumProperty 注解
        TypeEnumProperty typeEnumProperty = excelContentProperty.getField().getAnnotation(TypeEnumProperty.class);
        // 确保注解不为 null，并提供自定义错误消息
        Objects.requireNonNull(typeEnumProperty, "TypeEnumProperty annotation is missing on the field.");
        // 返回注解中定义的枚举类
        return typeEnumProperty.value();
    }

}
