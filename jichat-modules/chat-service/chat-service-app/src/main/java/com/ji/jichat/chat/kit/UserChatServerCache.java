package com.ji.jichat.chat.kit;

import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.user.api.vo.LoginUser;
import io.netty.channel.Channel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author jisl on 2024/1/25 9:52
 * 用户连接的服务信息缓存
 */
@Component
public class UserChatServerCache {

    @Resource
    private RedisTemplate<String, UserChatServerVO> redisTemplate;

    @Resource
    private TcpServerConfig tcpServerConfig;

    public void put(LoginUser loginUser, Channel channel) {
        final UserChatServerVO userChatServerVO = UserChatServerVO.builder()
                .userId(loginUser.getUserId()).deviceType(loginUser.getDeviceType())
                .outsideIp(tcpServerConfig.getOutsideIp()).tcpPort(tcpServerConfig.getTcpPort())
                .innerIp(tcpServerConfig.getInnerIp()).httpPort(tcpServerConfig.getHttpPort())
                .build();
        final String key = getKey(loginUser.getUserId(), loginUser.getDeviceType());
        redisTemplate.opsForValue().set(CacheConstant.USER_CHAT_SERVER + key, userChatServerVO, 8, TimeUnit.DAYS);
        ChannelRepository.put(key, channel);
    }

    public String getKey(long userId, Integer deviceType) {
        return userId + "_" + deviceType;
    }

    public UserChatServerVO get(long userId, Integer deviceType) {
        return redisTemplate.opsForValue().get(CacheConstant.USER_CHAT_SERVER + getKey(userId, deviceType));
    }

    public boolean remove(long userId, Integer deviceType) {
        final String key = getKey(userId, deviceType);
        ChannelRepository.remove(key);
        return redisTemplate.delete(CacheConstant.USER_CHAT_SERVER + key);
    }
}
