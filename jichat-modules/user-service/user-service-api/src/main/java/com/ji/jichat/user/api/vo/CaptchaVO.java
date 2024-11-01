package com.ji.jichat.user.api.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "管理后台 - 登录VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaptchaVO {


    @Schema(description =  "uuid", example = "20241101160347768245")
    private String uuid;

    @Schema(description =  "验证码", example = "nice")
    private String imgBase64;



}
