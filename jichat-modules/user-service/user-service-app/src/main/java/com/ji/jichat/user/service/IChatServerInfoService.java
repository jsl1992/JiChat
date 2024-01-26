package com.ji.jichat.user.service;

import com.ji.jichat.user.api.dto.ChatServerInfoDTO;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.entity.ChatServerInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 聊天服务器信息表 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
public interface IChatServerInfoService extends IService<ChatServerInfo> {

    void save(ChatServerInfoDTO dto);

    ChatServerInfoVO getByIpAndPort(String innerIp, int httpPort);
}
