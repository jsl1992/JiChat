package com.ji.jichat.chat.kit;

import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.user.api.vo.LoginUser;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jisl on 2024/1/25 9:52
 * 用户连接的服务信息缓存
 */
@Component
@Slf4j
public class UserChatServerCache {

    @Resource
    private RedisTemplate<String, UserChatServerVO> redisTemplate;


    @Resource
    private ServerLoadBalancer serverLoadBalancer;

    @Resource
    private TcpServerConfig tcpServerConfig;

    public void put(LoginUser loginUser, Channel channel) {
        final UserChatServerVO userChatServerVO = UserChatServerVO.builder()
                .userId(loginUser.getUserId()).deviceType(loginUser.getDeviceType())
                .outsideIp(tcpServerConfig.getOutsideIp()).tcpPort(tcpServerConfig.getTcpPort())
                .innerIp(tcpServerConfig.getInnerIp()).httpPort(tcpServerConfig.getHttpPort())
                .build();
        final String key = getUserKey(loginUser.getUserId(), loginUser.getDeviceType());
        redisTemplate.opsForValue().set(CacheConstant.USER_CHAT_SERVER + key, userChatServerVO, 8, TimeUnit.DAYS);
        ChannelRepository.put(key, channel);
        //增加当前的连接数
        serverLoadBalancer.incrementServerClientCount(tcpServerConfig.getHttpAddress());
    }

    public String getUserKey(long userId, int deviceType) {
        return userId + "_" + deviceType;
    }

    public UserChatServerVO get(String userKey) {
        return redisTemplate.opsForValue().get(CacheConstant.USER_CHAT_SERVER + userKey);
    }

    public boolean hasKey(long userId, int deviceType) {
        return hasKey(getUserKey(userId, deviceType));
    }

    public boolean hasKey(String userKey) {
        return redisTemplate.hasKey(CacheConstant.USER_CHAT_SERVER + userKey);
    }

    public boolean remove(String userKey) {
        log.info("开始执行移除{}客户端", userKey);
        serverLoadBalancer.subServerClientCount(tcpServerConfig.getHttpAddress());
        ChannelRepository.remove(userKey);
        return Boolean.TRUE.equals(redisTemplate.delete(CacheConstant.USER_CHAT_SERVER + userKey));
    }


    public void remove(Channel channel) {
        final String userKey = ChannelRepository.getUserKey(channel);
        if (Objects.nonNull(userKey)) {
            remove(userKey);
        }

    }
}
