package com.ji.jichat.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ChatServerInfoDTO", description = "聊天服务器信息表DTO")
public class ChatServerInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "外网IP", required = true, example = "192.168.137.179")
    private String outsideIp;

    @ApiModelProperty(value = "httpIp", required = true, example = "192.168.137.179")
    private String innerIp;

    @ApiModelProperty(value = "http端口", required = true, example ="18080")
    private Integer httpPort;

    @ApiModelProperty(value = "tcp端口", required = true, example ="7066")
    private Integer tcpPort;


}
