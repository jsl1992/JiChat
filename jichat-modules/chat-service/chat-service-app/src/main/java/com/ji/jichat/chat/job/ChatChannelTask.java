package com.ji.jichat.chat.job;

import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.netty.ChannelRepository;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ChatChannelTask {

    @Resource
    private UserChatServerCache userChatServerCache;

    @Resource
    private TcpServerConfig tcpServerConfig;


    /**
     * 移除无效的用户连接的channel （因为每个服务的连接不一样，所以需要把定时器放在ChatService里）
     *
     * @author jisl on 2024/1/21 12:32
     **/
    @Scheduled(cron = "0 0 * * * *") // 每小时执行一次
    public void removeInvalidChannelTask() {
        final Map<String, Channel> channelCache = ChannelRepository.getChannelCache();
        log.info("开始执行关闭无效连接客户端定时器:{}连接客户端总数", channelCache.size());
        for (Map.Entry<String, Channel> channelEntry : channelCache.entrySet()) {
            final String userKey = channelEntry.getKey();
            final UserChatServerVO userChatServerVO = userChatServerCache.get(userKey);
            if (Objects.isNull(userChatServerVO) || !Objects.equals(userChatServerVO.getHttpAddress(), tcpServerConfig.getHttpAddress())) {
//                不存在，或者连接到其他服务。那么需要将它关闭
                log.info("用户[{}]连接失效，关闭当前客户端连接", userKey);
                userChatServerCache.remove(userKey);
                channelEntry.getValue().close();
            }
        }
    }
}
