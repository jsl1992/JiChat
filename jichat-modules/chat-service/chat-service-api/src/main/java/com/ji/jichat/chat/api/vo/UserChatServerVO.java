package com.ji.jichat.chat.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author 芋道源码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChatServerVO implements Serializable {

    @ApiModelProperty(value = "外网ip", required = true, example = "192.168.77.130")
    private String outsideIp;

    @ApiModelProperty(value = "内网ip", required = true, example = "192.168.77.130")
    private String innerIp;

    @ApiModelProperty(value = "HTTP端口号",required = true, example = "18001")
    private Integer httpPort;

    @ApiModelProperty(value = "tcp端口号",required = true, example = "7801")
    private Integer tcpPort;

}
