package com.ji.jichat.client.netty.handler;


import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.ChatSendReturnMessage;
import com.ji.jichat.chat.api.dto.HeartBeatMessage;
import com.ji.jichat.chat.api.dto.Message;
import com.ji.jichat.chat.api.enums.ChatMessageTypeEnum;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.enums.DeviceTypeEnum;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.client.client.JiChatClient;
import com.ji.jichat.client.dto.ChatChannelDTO;
import com.ji.jichat.client.utils.JiDigitUtil;
import com.ji.jichat.common.enums.CommonStatusEnum;
import com.ji.jichat.common.util.GuardedObject;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
//@ChannelHandler.Sharable 用于标识一个可以在多个 Channel 上共享使用的 ChannelHandler
@ChannelHandler.Sharable
@Qualifier("bizClientHandler")
public class BizClientHandler extends ChannelInboundHandlerAdapter {

    public static final String NAME = "bizClientHandler";


    @Resource
    private ClientInfo clientInfo;

    @Resource
    private JiChatClient jiChatClient;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        Message message = (Message) obj;
        log.info("收到服务端消息:{}", message);
        if (message.getCode().equals(CommandCodeEnum.HEARTBEAT.getCode())) {
            HeartBeatMessage heartBeatMessage = (HeartBeatMessage) message;
        } else if (message.getCode().equals(CommandCodeEnum.PRIVATE_MESSAGE.getCode())) {
            GuardedObject.fireEvent(message.getNonce(), (ChatSendReturnMessage) message);
        } else if (message.getCode().equals(CommandCodeEnum.PRIVATE_MESSAGE_RECEIVE.getCode())) {
            ChatSendMessage chatSendMessage = (ChatSendMessage) message;
            //查看当前消息是否连续
            final Long curMaxMessageId = JiChatClient.chatMessageIdMap.get(chatSendMessage.getChannelKey());
            if (Objects.isNull(curMaxMessageId)) {
                log.error("当前消息，在客户端没有。需要触发全量同步消息操作");
            } else if (curMaxMessageId + 1 < chatSendMessage.getMessageId()) {
                log.warn("消息丢失，需要同步当前频道的消息");
            } else if (curMaxMessageId.equals(chatSendMessage.getMessageId())) {
                log.info("收到重复消息");
            } else {
                //更新当前的messageId
                JiChatClient.chatMessageIdMap.put(chatSendMessage.getChannelKey(), chatSendMessage.getMessageId());
            }
            final ChatMessageTypeEnum chatMessageTypeEnum = ChatMessageTypeEnum.getEnum(chatSendMessage.getMessageType());
            final ChatChannelDTO chatChannelDTO = JiChatClient.CHAT_CHANNEL_MAP.get(chatSendMessage.getMessageFrom());
            switch (chatMessageTypeEnum) {
                case TEXT:
                    if (chatSendMessage.getEncryptType().equals(CommonStatusEnum.ENABLE.getStatus())) {
                        //密文消息
                        if (Objects.equals(clientInfo.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                            if (Objects.equals(chatChannelDTO.getEncryptType(), CommonStatusEnum.ENABLE.getStatus())) {
                                final String decryptContent = JiDigitUtil.decryptAes(chatSendMessage.getMessageContent(), chatChannelDTO.getSecretKey(), chatSendMessage.getNonce());
                                log.info("解密后的明文:{}", decryptContent);
                            } else {
                                log.warn("当前手机客户端与用户{}的E2EE还没开启，请手动开启", chatSendMessage.getMessageFrom());
                            }
                        } else {
                            log.info("端到端加密请到手机端查看");
                        }
                    }
                    break;
                case RSA_PUBLIC_KEY:
                    if (Objects.equals(clientInfo.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                        //                    收到RSA公钥，那么生成E2EE 密钥发给对方。作为通信密钥
                        final String publicKey = chatSendMessage.getMessageContent();
                        final String secretKey = JiDigitUtil.genSecretKey(JiDigitUtil.AES_ALGORITHM);
                        chatChannelDTO.setSecretKey(secretKey);
                        chatChannelDTO.setEncryptType(CommonStatusEnum.ENABLE.getStatus());
                        //E2EE密钥用 RSA公钥加密，发给对方
                        final String encryptRsa = JiDigitUtil.encryptRsa(secretKey, publicKey);
                        jiChatClient.privateMessage(encryptRsa, ChatMessageTypeEnum.END_TO_END_KEY.getCode(), chatSendMessage.getMessageFrom());
                    } else {
                        log.info("密钥连接请求");
                    }
                    break;
                case END_TO_END_KEY:
                    if (Objects.equals(clientInfo.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                        // 收到E2EE 密钥
                        final String encryptRsa = chatSendMessage.getMessageContent();
                        final String secretKey = JiDigitUtil.decryptRsa(encryptRsa, chatChannelDTO.getPrivateKey());
                        chatChannelDTO.setSecretKey(secretKey);
                        chatChannelDTO.setEncryptType(CommonStatusEnum.ENABLE.getStatus());
                    } else {
                        log.info("密钥连接请求");
                    }
                    break;
                case END_TO_END_CLOSE:
                    if (Objects.equals(clientInfo.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                        chatChannelDTO.setEncryptType(CommonStatusEnum.DISABLE.getStatus());
                    } else {
                        log.info("收到关闭E2EE");
                    }
                    break;
            }
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        长时间，没有发消息给服务端，开始发送心跳
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                sendPing(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private void sendPing(ChannelHandlerContext ctx) {
        final HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
        clientInfo.fillMessage(heartBeatMessage);
        heartBeatMessage.setCode(CommandCodeEnum.HEARTBEAT.getCode());
        heartBeatMessage.setContent("ping");
        log.info("发送给服务端心跳:{},服务端:{}", heartBeatMessage.getContent(), clientInfo.getUserChatServerVO().getHttpAddress());
        ctx.writeAndFlush(heartBeatMessage).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("IO error,close Channel");
                future.channel().close();
            }
        });
    }

}
