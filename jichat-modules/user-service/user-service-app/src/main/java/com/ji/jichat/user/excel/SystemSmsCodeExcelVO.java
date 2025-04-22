package com.ji.jichat.user.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.excel.util.annotations.KeyValueConverterProperty;
import com.ji.jichat.excel.util.annotations.TypeEnumProperty;
import com.ji.jichat.excel.util.converter.KeyValueConverter;
import com.ji.jichat.excel.util.converter.TypeEnumConverter;
import com.ji.jichat.user.api.enums.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 手机验证码VO
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "手机验证码VO")
public class SystemSmsCodeExcelVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @NotNull
    @ExcelProperty
    private Long id;

    @Schema(description = "手机号")
    @NotBlank
    @ExcelProperty
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank
    @ExcelProperty
    private String code;

    @Schema(description = "创建 IP")
    @NotBlank
    @ExcelProperty
    private String createIp;


    @TypeEnumProperty(value = SmsSceneEnum.class)
    @Schema(description = "发送场景")
    @NotNull
    @ExcelProperty(converter = TypeEnumConverter.class)
    private Integer scene;

    @Schema(description = "今日发送的第几条")
    @NotNull
//    @ExcelProperty(converter = KeyValueConverter.class)
//    @KeyValueConverterProperty(value = "1=一、2=二")
    private Integer todayIndex;

    @Schema(description = "是否使用")
    @NotNull
    @ExcelProperty
    private Integer used;

    @Schema(description = "使用时间")
    @NotNull
    @ExcelProperty
    private Date usedTime;

    @Schema(description = "使用 IP")
    @NotBlank
    @ExcelProperty
    private String usedIp;


}
