package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema( description = "聊天服务器信息表")
public class ChatServerInfo extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description="id主键")
    private Long id;

    @Schema(description="外网IP")
    private String outsideIp;

    @Schema(description="httpIp")
    private String innerIp;

    @Schema(description="http端口")
    private Integer httpPort;

    @Schema(description="tcp端口")
    private Integer tcpPort;

    @Schema(description="状态（0停用 1正常）")
    private Integer status;


}
