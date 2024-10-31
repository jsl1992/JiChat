package com.ji.jichat.client.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-24
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "ChatChannelDTO")
public class ChatChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "密钥")
    private String channelKey;

    @Schema(description = "Rsa公钥")
    private String publicKey;

    @Schema(description = "Rsa私钥")
    private String privateKey;

    @Schema(description = "E2EE 密钥")
    private String secretKey;

    @Schema(description = "加密类型 0:无加密 1:E2EE端到端加密")
    private Integer encryptType;


}
