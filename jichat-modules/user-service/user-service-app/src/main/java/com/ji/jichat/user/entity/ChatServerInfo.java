package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 聊天服务器信息表
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_chat_server_info")
@ApiModel(value = "ChatServerInfo对象", description = "聊天服务器信息表")
public class ChatServerInfo extends BaseDO {

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
