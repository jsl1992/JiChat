package com.ji.jichat.user.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("路由TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteServerVO {


    @ApiModelProperty(value = "外网ip", required = true, example = "192.168.77.130")
    private String outsideIp;

    @ApiModelProperty(value = "内网ip", required = true, example = "192.168.77.130")
    private String innerIp;

    @ApiModelProperty(value = "端口号",required = true, example = "18001")
    private String port;



}
