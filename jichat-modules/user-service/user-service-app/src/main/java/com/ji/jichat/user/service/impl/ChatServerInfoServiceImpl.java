package com.ji.jichat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ji.jichat.common.enums.CommonStatusEnum;
import com.ji.jichat.user.api.dto.ChatServerInfoDTO;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.convert.ChatServerInfoConvert;
import com.ji.jichat.user.entity.ChatServerInfo;
import com.ji.jichat.user.mapper.ChatServerInfoMapper;
import com.ji.jichat.user.service.IChatServerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天服务器信息表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@Service
public class ChatServerInfoServiceImpl extends ServiceImpl<ChatServerInfoMapper, ChatServerInfo> implements IChatServerInfoService {

    @Override
    public void save(ChatServerInfoDTO dto) {
        save(ChatServerInfoConvert.INSTANCE.convert(dto));
    }

    @Override
    public ChatServerInfoVO getByIpAndPort(String innerIp, int httpPort) {
        final ChatServerInfo chatServerInfo = getOne(new LambdaQueryWrapper<ChatServerInfo>()
                .eq(ChatServerInfo::getInnerIp, innerIp)
                .eq(ChatServerInfo::getHttpPort, httpPort)
                .eq(ChatServerInfo::getStatus, CommonStatusEnum.ENABLE));
        return ChatServerInfoConvert.INSTANCE.convert(chatServerInfo);
    }
}
