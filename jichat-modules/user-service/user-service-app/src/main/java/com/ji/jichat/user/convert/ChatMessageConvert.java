package com.ji.jichat.user.convert;

import java.util.*;


import com.ji.jichat.chat.api.dto.ChatSendMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.ChatMessage;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;

/**
 * 聊天信息表 Convert
 *
 * @author jisl
 * @since 2024-01-28
 */
@Mapper
public interface ChatMessageConvert {

    ChatMessageConvert INSTANCE = Mappers.getMapper(ChatMessageConvert.class);

    ChatMessage convert(ChatMessageVO bean);

    ChatMessage convert(ChatMessageDTO bean);

    ChatMessageVO convert(ChatMessage bean);

    List<ChatMessageVO> convertList(List<ChatMessage> list);

    ChatMessage convert(ChatSendMessage bean);




}
