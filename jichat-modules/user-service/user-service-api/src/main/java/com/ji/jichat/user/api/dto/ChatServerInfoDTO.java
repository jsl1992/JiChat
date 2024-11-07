package com.ji.jichat.user.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 聊天服务器信息表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema( description = "聊天服务器信息表DTO")
public class ChatServerInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description =  "外网IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.137.179")
    private String outsideIp;

    @Schema(description =  "httpIp", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.137.179")
    private String innerIp;

    @Schema(description =  "http端口", requiredMode = Schema.RequiredMode.REQUIRED, example ="18080")
    private Integer httpPort;

    @Schema(description =  "tcp端口", requiredMode = Schema.RequiredMode.REQUIRED, example ="7066")
    private Integer tcpPort;


}
