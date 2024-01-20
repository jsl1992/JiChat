package com.ji.jichat.chat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.enums.CommandCodeEnum;
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
import java.util.concurrent.TimeUnit;

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

//    @Resource

    @Resource
    private TcpServerConfig tcpServerConfig;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, UpMessage msg) throws Exception {
        final Channel channel = ChannelRepository.get(msg.getLoginKey());
        if (channel == null && !msg.isMatch(CommandCodeEnum.LOGIN.getCode())) {
            //之前没有登录，且当前编码不是登录那么需要返回异常
            log.debug("第[{}]次收到连接[{},{}]未登录,丢弃请求msg=[{}]", ++count, ctx.channel().remoteAddress(), msg.getLoginKey(), msg);
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
        log.debug("收到连接[{}] 登录请求msg=[{}]", ctx.channel().remoteAddress(), msg);
        final LoginUser loginUser = getLoginUser(msg);
        if (Objects.isNull(loginUser) || Objects.equals(loginUser.getLoginKey(), msg.getLoginKey())) {
            log.warn("连接[{}]登录请求token为空,关闭连接", ctx.channel().remoteAddress());
            ctx.channel().close();
            return;
        }
        ChannelRepository.put(msg.getLoginKey(), ctx.channel());
        final UserChatServerVO userChatServerVO = UserChatServerVO.builder()
                .httpPort(tcpServerConfig.getHttpPort()).tcpPort(tcpServerConfig.getTcpPort())
                .outsideIp(tcpServerConfig.getOutsideIp()).innerIp(tcpServerConfig.getInnerIp())
                .build();
        redisTemplate.opsForValue().set(CacheConstant.LOGIN_USER, userChatServerVO, 8, TimeUnit.DAYS);
        ctx.pipeline().remove(this);
        log.info("[{}-{}]建立连接登录成功,初始化session,httpTimestamp=[{}]", msg.getLoginKey(), msg.getDeviceType());
    }

    private LoginUser getLoginUser(UpMessage msg) {
        try {
            String token = JSONObject.parseObject(msg.getContent()).getString("token");
            final String loginKey = JwtUtil.validateJwtWithGetSubject(token);
            if (!Objects.equals(msg.getLoginKey(), loginKey)) {
                return null;
            }
            LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(CacheConstant.LOGIN_USER + loginKey);
            return loginUser;
        } catch (Exception e) {
            log.info("解析token获取用户异常:", e);
        }
        return null;
    }


}
