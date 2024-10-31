package com.ji.jichat.user.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Schema(description = "聊天信息表DTO")
public class ChatMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "聊天频道id", example = "cOvWLMNlsma_cOkLiKpPVqd")
    @NotEmpty
    private String channelKey;

    @Schema(description = "信息id-开始", example = "0")
    @NotNull
    private Long messageIdStart;

    @Schema(description = "信息id-结束", example = "1000")
    @NotNull
    private Long messageIdEnd;

    @Schema(description = "消息类型 1：文字 2：图片 3：语音 4：视频 5：文件")
    private Integer messageType;


}
