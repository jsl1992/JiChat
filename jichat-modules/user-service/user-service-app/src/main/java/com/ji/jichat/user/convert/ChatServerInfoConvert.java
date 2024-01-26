package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.ChatServerInfo;
import com.ji.jichat.user.api.dto.ChatServerInfoDTO;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;

/**
 * 聊天服务器信息表 Convert
 *
 * @author jisl
 * @since 2024-01-26
 */
@Mapper
public interface ChatServerInfoConvert {

    ChatServerInfoConvert INSTANCE = Mappers.getMapper(ChatServerInfoConvert.class);

    ChatServerInfo convert(ChatServerInfoVO bean);

    ChatServerInfo convert(ChatServerInfoDTO bean);

    ChatServerInfoVO convert(ChatServerInfo bean);

    List<ChatServerInfoVO> convertList(List<ChatServerInfo> list);




}
