package com.ji.jichat.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 聊天信息表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "ChatMessageDTO", description = "聊天信息表DTO")
public class ChatMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("聊天频道id")
    @NotEmpty
    private String channelKey;

    @ApiModelProperty("信息id-开始")
    @NotNull
    private Long messageIdStart;

    @ApiModelProperty("信息id-结束")
    @NotNull
    private Long messageIdEnd;

    @ApiModelProperty("消息类型 1：文字 2：图片 3：语音 4：视频 5：文件")
    private Integer messageType;


}
