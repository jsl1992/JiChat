package com.ji.jichat.user.service;

import com.ji.jichat.chat.api.dto.ChatMessageSendDTO;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import com.ji.jichat.user.entity.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 聊天信息表 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
public interface IChatMessageService extends IService<ChatMessage> {


    List<ChatMessageVO> query(ChatMessageDTO chatMessageDTO);

    void saveMessage(ChatMessageSendDTO chatMessageSendDTO);
}
