package com.ji.jichat.chat.netty.handler;


import com.ji.jichat.chat.api.enums.MessageTypeEnum;
import com.ji.jichat.chat.convert.MessageConvert;
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

@Slf4j
@Component
@Qualifier("bizServerHandler")
//@ChannelHandler.Sharable 用于标识一个可以在多个 Channel 上共享使用的 ChannelHandler
@ChannelHandler.Sharable
public class BizServerHandler extends SimpleChannelInboundHandler<UpMessage> {

    public static final String NAME = "BizServerHandler";


    @Resource
    private StrategyContext strategyContext;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, UpMessage message) {
        log.info("收到客户端消息:{}", message);
        final long start = System.currentTimeMillis();
        try {
            final CommandStrategy processor = strategyContext.getProcessor(message.getCode());
            final String returnContent = processor.execute(message);
            final DownMessage downMessage = MessageConvert.INSTANCE.convert(message);
            downMessage.setType(MessageTypeEnum.DOWN.getCode());
            downMessage.setContent(returnContent);
            log.info("回复客户端消息:{}", downMessage);
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
//            ReferenceCountUtil.release(message) 是 Netty 框架中的一个方法，用于释放 Netty 对象的引用计数。在 Netty 中，对象的引用计数是一种用于跟踪对象在内存中的引用数量的机制，以确保在不再需要使用对象时能够安全地释放资源
            ReferenceCountUtil.release(message);
        }

    }

}
