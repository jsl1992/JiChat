package com.ji.jichat.excel.util.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ji.jichat.excel.util.annotations.KeyValueConverterProperty;

import java.util.Objects;


public class KeyValueConverter implements Converter<Integer> {

    /**
     * key转成value
     **/
    private static final Integer KEY_TO_VALUE = 1;

    /**
     * value转成key
     **/
    private static final Integer VALUE_TO_KEY = 2;


    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        final String value = cellData.getStringValue();
        String converterExp = getConverterExp(excelContentProperty);
        final Object key = convertByExp(value, converterExp, VALUE_TO_KEY);
        return value.equals(key) ? null : (Integer) key;
    }


    @Override
    public WriteCellData convertToExcelData(Integer key, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        String converterExp = getConverterExp(excelContentProperty);
        final String value = convertByExp(key + "", converterExp, KEY_TO_VALUE) + "";
        return new WriteCellData(value);
    }

    private String getConverterExp(ExcelContentProperty excelContentProperty) {
        final KeyValueConverterProperty keyValueProperty = excelContentProperty.getField().getAnnotation(KeyValueConverterProperty.class);
        Objects.requireNonNull(keyValueProperty);
        return keyValueProperty.value();
    }

    /**
     * @param propertyValue 需要转换的值
     * @param converterExp  转换表达式,如:0=未知,1=男,2=女
     * @param convertType   转换类型
     * @return java.lang.Object  转换后的值
     * @author jishenglong on 2019/11/28 14:41
     **/
    private static Object convertByExp(String propertyValue, String converterExp, Integer convertType) {
        try {
//            // 将转换表达式以逗号分隔成多个项
            String[] convertSource = converterExp.trim().split(",");
            for (String item : convertSource) {
//                 // 将每项以等号分隔为键和值，例如 "1=男" 分割为 ["1", "男"]
                String[] itemArray = item.split("=");
                if (KEY_TO_VALUE.equals(convertType)) {
                    if (itemArray[0].equals(propertyValue)) {
                        return itemArray[1];
                    }
                } else {
                    if (itemArray[1].equals(propertyValue)) {
                        return itemArray[0];
                    }
                }

            }
        } catch (Exception ignored) {
        }
        return propertyValue;
    }


    public static void main(String[] args) {
        final Object o = convertByExp("2", "2=支付宝,3=微信", KEY_TO_VALUE);
        System.out.println(o);

    }
}
