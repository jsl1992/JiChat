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

    @ApiModelProperty(value = "消息类型 1：文字 2：图片 3：语音 4：视频 5：文件", required = true, example = "1")
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
