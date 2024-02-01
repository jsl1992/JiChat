package com.ji.jichat.chat.api.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class ChatSendReturnMessage extends Message {

    @ApiModelProperty(value = "消息id", required = true, example = "1")
    private Long messageId;

    @ApiModelProperty(value = "消息创建时间", required = true, example = "1")
    private Date createTime;


}
