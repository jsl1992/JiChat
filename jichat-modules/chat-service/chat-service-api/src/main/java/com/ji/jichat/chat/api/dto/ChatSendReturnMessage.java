package com.ji.jichat.chat.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel("用户TCP服务")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSendReturnMessage extends Message {

    @ApiModelProperty(value = "消息id", required = true, example = "1")
    private Long messageId;

    @ApiModelProperty(value = "消息创建时间", required = true, example = "1")
    private Date createTime;


}
