package com.ji.jichat.chat.api.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "用户TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatServerVO implements Serializable {

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.77.130")
    private Long userId;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer deviceType;

    @Schema(description = "内网ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.77.130")
    private String innerIp;

    @Schema(description = "tcp端口号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18001")
    private Integer httpPort;

    @Schema(description = "外网ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "182.168.77.130")
    private String outsideIp;

    @Schema(description = "tcp端口号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7800")
    private Integer tcpPort;


    public String getHttpAddress() {
        return this.innerIp + ":" + this.httpPort;
    }

    public String getTcpAddress() {
        return this.outsideIp + ":" + this.tcpPort;
    }


}
