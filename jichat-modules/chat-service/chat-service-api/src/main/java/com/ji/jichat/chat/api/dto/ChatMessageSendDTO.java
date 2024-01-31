package com.ji.jichat.chat.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel("用户TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageSendDTO implements Serializable {

    @ApiModelProperty(value = "用户id", required = true, example = "1")
    private Long messageFrom;

    @ApiModelProperty(value = "用户id", required = true, example = "1")
    private Long messageTo;

    @ApiModelProperty(value = "消息加密类型 0：无 1：端到端加密E2EE", required = true, example = "1")
    private Integer encryptType;

    @ApiModelProperty(value = "消息类型 101：文字 102：图片 103：语音 104：视频 105：文件 201:RSA公钥 202:端到端密钥", required = true, example = "1")
    private Integer messageType;


    @ApiModelProperty(value = "消息内容", required = true, example = "HelloWorld")
    private String messageContent;

    @ApiModelProperty("频道key")
    private String channelKey;

    @ApiModelProperty(value = "消息id", required = true, example = "1")
    private Long messageId;

    @ApiModelProperty(value = "消息创建时间", required = true, example = "1")
    private Date createTime;


}
