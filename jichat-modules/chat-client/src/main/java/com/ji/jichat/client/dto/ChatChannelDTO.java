package com.ji.jichat.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ChatChannelDTO")
public class ChatChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("密钥")
    private String channelKey;

    @ApiModelProperty("Rsa公钥")
    private String publicKey;

    @ApiModelProperty("Rsa私钥")
    private String privateKey;

    @ApiModelProperty("E2EE 密钥")
    private String secretKey;

    @ApiModelProperty("加密类型 0:无加密 1:E2EE端到端加密")
    private Integer encryptType;


}
