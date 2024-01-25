package com.ji.jichat.chat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.kit.ServerLoadBalancer;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.pojo.UpMessage;
import com.ji.jichat.security.admin.utils.JwtUtil;
import com.ji.jichat.user.api.vo.LoginUser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 *
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class LoginHandler extends SimpleChannelInboundHandler<UpMessage> {


    private static final int MAX_NO_LOGIN_PROTOCOL_COUNT = 3;

    private int count = 0;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ServerLoadBalancer serverLoadBalancer;
    @Resource
    private TcpServerConfig tcpServerConfig;

    @Resource
    private UserChatServerCache userChatServerCache;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, UpMessage msg) throws Exception {
        final String key = userChatServerCache.getKey(msg.getUserId(), msg.getDeviceType());
        final Channel channel = ChannelRepository.get(key);
        if (channel == null && !msg.isMatch(CommandCodeEnum.LOGIN.getCode())) {
            //之前没有登录，且当前编码不是登录那么需要返回异常
            log.debug("第[{}]次收到连接[{},{}]未登录,丢弃请求msg=[{}]", ++count, ctx.channel().remoteAddress(), key, msg);
            if (count >= MAX_NO_LOGIN_PROTOCOL_COUNT) {
                log.error("连接[{}]超过最大未登录发送连接次数[{}],关闭连接", ctx.channel().remoteAddress(), MAX_NO_LOGIN_PROTOCOL_COUNT);
                ctx.channel().close();
            }
            return;
        } else if (!msg.isMatch(CommandCodeEnum.LOGIN.getCode())) {
            log.debug("收到连接[{}] 请求msg=[{}],remove LoginHandler", msg.getClientIp(), msg);
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
            return;
        }
        log.info("收到连接[{}] 登录请求msg=[{}]", ctx.channel().remoteAddress(), msg);
        final LoginUser loginUser = getLoginUser(msg);
        if (Objects.isNull(loginUser)) {
            log.warn("连接[{}]登录请求token为空,关闭连接", ctx.channel().remoteAddress());
            ctx.channel().close();
            return;
        }
        //缓存用户连接的服务信息
        userChatServerCache.put(loginUser, ctx.channel());
        //增加当前的连接数
        serverLoadBalancer.incrementServerClientCount(tcpServerConfig.getHttpAddress());
//        理器在处理完特定的任务后，不再需要继续处理后续的事件。通过调用 remove(this) 可以将该处理器从链中移除，防止后续的事件传递给它。这在某些场景下有助于提高性能或确保在适当的时候清理资源
        ctx.pipeline().remove(this);
        log.info("[{}]建立连接登录成功,初始化session", key);
    }

    private LoginUser getLoginUser(UpMessage msg) {
        try {
            String token = JSONObject.parseObject(msg.getContent()).getString("token");
            final String loginKey = JwtUtil.validateJwtWithGetSubject(token);
            return (LoginUser) redisTemplate.opsForValue().get(CacheConstant.LOGIN_USER + loginKey);
        } catch (Exception e) {
            log.info("解析token获取用户异常:", e);
        }
        return null;
    }


}
