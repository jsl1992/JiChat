package com.ji.jichat.chat.convert;

import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.ChatSendReturnMessage;
import com.ji.jichat.chat.api.dto.UpMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Message Convert
 *
 * @author jisl
 * @since 2024-01-24
 */
@Mapper
public interface MessageConvert {

    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    ChatSendReturnMessage convert(ChatSendMessage bean);

//    ChatSendMessage convert(ChatSendMessage bean);


}
