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
 * 聊天信息表VO
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "ChatMessageVO", description = "聊天信息表VO")
public class ChatMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("信息id")
    private Long messageId;

    @ApiModelProperty("信息来源")
    private Long messageFrom;

    @ApiModelProperty("信息到达")
    private Long messageTo;

    @ApiModelProperty("消息类型 1：文字 2：图片 3：语音 4：视频 5：文件")
    private Integer messageType;

    @ApiModelProperty("信息内容")
    private String messageContent;


}
