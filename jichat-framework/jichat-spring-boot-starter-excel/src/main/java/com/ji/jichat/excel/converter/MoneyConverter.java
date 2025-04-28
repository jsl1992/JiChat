package com.ji.jichat.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ji.jichat.common.util.MoneyUtils;

/**
 * EasyExcel Money 转换
 */
public class MoneyConverter implements Converter<Number> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Number convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        final Number number = MoneyUtils.dollar2centNumber(cellData.getNumberValue().doubleValue());
        if (number.longValue() > Integer.MAX_VALUE) {
            return number.longValue();
        } else {
            return number.intValue();
        }
    }

    @Override
    public WriteCellData convertToExcelData(Number value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(MoneyUtils.cent2dollarBD(value));
    }
}
