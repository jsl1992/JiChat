package com.ji.jichat.user.api.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 聊天服务器信息表VO
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "聊天服务器信息表VO")
public class ChatServerInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "外网IP")
    private String outsideIp;

    @Schema(description = "httpIp")
    private String innerIp;

    @Schema(description = "http端口")
    private Integer httpPort;

    @Schema(description = "tcp端口")
    private Integer tcpPort;

    @Schema(description = "状态（0停用 1正常）")
    private Integer status;


}
