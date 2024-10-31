package com.ji.jichat.chat.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ChatSendMessage extends Message {

    @Schema(description =  "用户id", required = true, example = "1")
    private Long messageFrom;

    @Schema(description =  "用户id", required = true, example = "1")
    private Long messageTo;

    @Schema(description =  "消息加密类型 0：无 1：端到端加密E2EE", required = true, example = "1")
    private Integer encryptType;

    @Schema(description =  "消息类型 101：文字 102：图片 103：语音 104：视频 105：文件 201:RSA公钥 202:端到端密钥", required = true, example = "1")
    private Integer messageType;


    @Schema(description =  "消息内容", required = true, example = "HelloWorld")
    private String messageContent;

    @Schema(description ="频道key")
    private String channelKey;

    @Schema(description =  "消息id", required = true, example = "1")
    private Long messageId;

    @Schema(description =  "消息创建时间", required = true, example = "1")
    private Date createTime;


}
