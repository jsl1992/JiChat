package com.ji.jichat.user.api.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ChatServerInfoVO", description = "聊天服务器信息表VO")
public class ChatServerInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("外网IP")
    private String outsideIp;

    @ApiModelProperty("httpIp")
    private String innerIp;

    @ApiModelProperty("http端口")
    private Integer httpPort;

    @ApiModelProperty("tcp端口")
    private Integer tcpPort;

    @ApiModelProperty("状态（0停用 1正常）")
    private Integer status;


}
