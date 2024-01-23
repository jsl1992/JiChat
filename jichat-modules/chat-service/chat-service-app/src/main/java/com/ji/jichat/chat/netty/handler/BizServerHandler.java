package com.ji.jichat.chat.netty.handler;


import cn.hutool.core.bean.BeanUtil;
import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.chat.strategy.StrategyContext;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.UpMessage;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@Qualifier("bizServerHandler")
//@ChannelHandler.Sharable 用于标识一个可以在多个 Channel 上共享使用的 ChannelHandler
@ChannelHandler.Sharable
public class BizServerHandler extends SimpleChannelInboundHandler<UpMessage> {

    public static final String NAME = "BizServerHandler";

    // 所有消息数量
    private final AtomicLong allMsgCount = new AtomicLong(0);

    @Resource
    private StrategyContext strategyContext;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, UpMessage message) {
        log.info("收到客户端消息:{}", message);
        final long start = System.currentTimeMillis();
        try {
            final CommandStrategy processor = strategyContext.getProcessor(message.getCode());
            final String returnContent = processor.execute(message);
            final DownMessage downMessage = BeanUtil.toBean(message, DownMessage.class);
            downMessage.setContent(returnContent);
            log.info("返回客户端消息:{}", downMessage);
            ctx.channel().writeAndFlush(downMessage).addListener((ChannelFutureListener) future -> {
                final long time = System.currentTimeMillis() - start;
                if (!future.isSuccess()) {
                    log.error("业务处理回复相机失败,ip=[{}],code=[{}],耗时[{}]ms", message.getClientIp(), message.getCode(), time);
                    return;
                }
                if (time > 10000) {
                    log.warn("业务处理回复相机异常耗时,ip=[{}],code=[{}],耗时[{}]ms", message.getClientIp(), message.getCode(), time);
                }
            });
        } catch (Exception e) {
            log.error("业务处理异常:{},e:{}", message, e.getMessage(), e);
        } finally {
            log.debug("业务处理完成:code={},ip={}", message.getCode(), message.getClientIp());
            ReferenceCountUtil.release(message);
        }

    }

}
