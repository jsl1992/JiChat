package com.ji.jichat.user.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel("管理后台 - 登录VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginVO {

    @ApiModelProperty(value = "用户id", required = true, example = "1024")
    private Long userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "happy")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "nice")
    private String refreshToken;

    @ApiModelProperty(value = "accessToken过期时间", required = true)
    private Date expiresTime;



}
