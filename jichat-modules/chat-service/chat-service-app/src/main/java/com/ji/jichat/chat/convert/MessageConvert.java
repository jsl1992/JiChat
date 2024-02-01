package com.ji.jichat.chat.convert;

import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.ChatSendReturnMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Message Convert
 *
 * @author jisl
 * @since 2024-01-24
 */
@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true))
public interface MessageConvert {

    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    ChatSendReturnMessage convertReturn(ChatSendMessage bean);

    ChatSendMessage convert(ChatSendMessage bean);


}
