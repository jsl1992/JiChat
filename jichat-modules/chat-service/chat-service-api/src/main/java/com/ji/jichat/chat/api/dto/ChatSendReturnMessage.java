package com.ji.jichat.chat.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author jisl on 2024/2/1 9:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ChatSendReturnMessage extends Message {

    @Schema(description =  "消息id",  requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long messageId;

    @Schema(description =  "消息创建时间",  requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Date createTime;


}
