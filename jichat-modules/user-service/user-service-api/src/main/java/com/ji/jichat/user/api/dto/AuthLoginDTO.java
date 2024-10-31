package com.ji.jichat.user.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;



@Schema(description = "管理后台 - AuthLoginDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginDTO {

    @Schema(description =  "账号", required = true, example = "jisl")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @Schema(description =  "密码", required = true, example = "12345678")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度为 8-16 位")
    private String password;


    /**
     * 设备标识
     */
    @Schema(description =  "设备标识", required = true, example = "jpOKnf5vGaW9IcitjEPrUGINjDSSEvXd")
    @NotEmpty(message = "设备标识不能为空")
    private String deviceIdentifier;

    @Schema(description =  "设备名称", required = true, example = "iPhoneX")
    @NotEmpty(message = "设备名称不能为空")
    private String deviceName;

    @Schema(description =  "设备类型 1手机 2电脑 3平板", required = true, example = "1")
    private Integer deviceType;

    @Schema(description =  "操作系统类型 101安卓，102iOS,201 Windows，202 Linux，301 iPad,302 安卓平板 ", required = true, example = "102")
    private Integer osType;


}
