package com.ji.jichat.chat.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@ApiModel("用户TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatServerVO implements Serializable {

    @ApiModelProperty(value = "用户id", required = true, example = "192.168.77.130")
    private Long userId;

    @ApiModelProperty(value = "设备类型", required = true, example = "1")
    private Integer deviceType;

    @ApiModelProperty(value = "内网ip", required = true, example = "192.168.77.130")
    private String innerIp;

    @ApiModelProperty(value = "tcp端口号", required = true, example = "18001")
    private Integer httpPort;

    @ApiModelProperty(value = "外网ip", required = true, example = "182.168.77.130")
    private String outsideIp;

    @ApiModelProperty(value = "tcp端口号", required = true, example = "7800")
    private Integer tcpPort;


    public String getHttpAddress() {
        return this.outsideIp + ":" + this.httpPort;
    }

    public String getTcpAddress() {
        return this.outsideIp + ":" + this.tcpPort;
    }


}
