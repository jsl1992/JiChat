package com.ji.jichat.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ji.jichat.chat.api.dto.ChatMessageSendDTO;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import com.ji.jichat.user.convert.ChatMessageConvert;
import com.ji.jichat.user.entity.ChatMessage;
import com.ji.jichat.user.mapper.ChatMessageMapper;
import com.ji.jichat.user.service.IChatMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 聊天信息表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {

    @Override
    public List<ChatMessageVO> query(ChatMessageDTO chatMessageDTO) {
        //todo 这边校验下，查询范围是否合法（不能查询不是好友列表）
        return baseMapper.query(chatMessageDTO);
    }

    @Override
    public void saveMessage(ChatMessageSendDTO chatMessageSendDTO) {
        final ChatMessage chatMessage = ChatMessageConvert.INSTANCE.convert(chatMessageSendDTO);
        chatMessage.setMessageContent(chatMessageSendDTO.getMessageContent());
        save(chatMessage);
    }
}
