package com.ji.jichat.user.mapper;

import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import com.ji.jichat.user.entity.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 聊天信息表 Mapper 接口
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    List<ChatMessageVO> query(ChatMessageDTO chatMessageDTO);
}
