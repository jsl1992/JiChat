package com.ji.jichat.user.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Schema( description = "用户注册")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {

    @Schema(description =  "账号", required = true, example = "jisl")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;


    @Schema(description =  "用户昵称", required = true, example = "纪大侠")
    @NotEmpty(message = "用户昵称不能为空")
    @Length(min = 2, max = 50, message = "用户昵称长度为 2-50 位")
    private String nickname;

    @Schema(description =  "密码", required = true, example = "12345678")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度为 8-16 位")
    private String password;

    @Schema(description =  "手机号", required = true, example = "18012345678")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;


}
