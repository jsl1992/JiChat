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
public class AuthLoginVO {

    @Schema(description =  "用户id", required = true, example = "1024")
    private Long userId;

    @Schema(description =  "访问令牌", required = true, example = "happy")
    private String accessToken;

    @Schema(description =  "刷新令牌", required = true, example = "nice")
    private String refreshToken;

    @Schema(description =  "accessToken过期时间", required = true)
    private Date expiresTime;


}
