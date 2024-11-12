package com.ji.jichat.excel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ji.jichat.excel.util.annotations.KeyValueConverterProperty;
import com.ji.jichat.excel.util.annotations.TypeEnumProperty;
import com.ji.jichat.excel.util.converter.KeyValueConverter;
import com.ji.jichat.excel.util.converter.MoneyConverter;
import com.ji.jichat.excel.util.converter.TypeEnumConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 券购买记录分页查询返回bean
 *
 * @author jishenglong on 2019/11/19 14:34
 **/
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CouponPurchaseDTO {


    @Schema(description = "购买方式，1：后台发放、2：自助购买  3:清除发券")
    @ExcelProperty(value = "购买方式", converter = TypeEnumConverter.class)
    @TypeEnumProperty(CommonEnum.class)
    private Integer purchaseType;

    @Schema(description = "支付方式:2=支付宝,3=微信")
    @KeyValueConverterProperty("2=支付宝,3=微信")
    @ExcelProperty(value = "支付方式", converter = KeyValueConverter.class)
    private Integer paymentType;

    @Schema(description = "支付金额")
    @ExcelProperty(value = "支付金额", converter = MoneyConverter.class)
    private Integer paymentAmount;

    @Schema(description = "购买时间")
    @ExcelProperty("购买时间")
    private Date paymentTime;

    @Schema(description = "单价")
    @ExcelProperty(value = "单价", converter = MoneyConverter.class)
    private Integer unitPrice;


}
