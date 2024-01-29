package com.ji.jichat.client.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.dto.ChatMessageSendDTO;
import com.ji.jichat.chat.api.enums.ChatMessageTypeEnum;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.client.dto.AppUpMessage;
import com.ji.jichat.client.manager.JiChatServerManager;
import com.ji.jichat.client.netty.ClientChannelInitializer;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.common.pojo.UpMessage;
import com.ji.jichat.common.util.GuardedObject;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import com.ji.jichat.user.api.vo.UserRelationVO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 22/05/2018 14:19
 * @since JDK 1.8
 */
@Component
@Slf4j
public class JiChatClient implements CommandLineRunner {


    private final EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("jichat-work"));


    private SocketChannel channel;


    @Resource
    private JiChatServerManager jiChatServerManager;

    @Resource
    private ClientChannelInitializer clientChannelInitializer;


    @Resource
    private ClientInfo clientInfo;


    private static final ArrayDeque<UpMessage> messagesQueue = new ArrayDeque<>();

    public static HashMap<String, Long> chatMessageIdMap = new HashMap<>();


    private static final HashMap<Long, String> channelKeyMap = new HashMap<>();

    /**
     * 重试次数
     */
    private int errorCount;

    public void start() {
        //登录 + 获取可以使用的服务器 ip+port
        jiChatServerManager.userLogin();
        //启动客户端
        startClient();
        //向服务端注册
        loginTcpServer();
        //同步历史消息
        syncHisMsg();
    }

    public void syncHisMsg() {
        final List<UserRelationVO> userRelations = jiChatServerManager.listUserRelation();
        //遍历所有的好友列表
        for (UserRelationVO vo : userRelations) {
            final String channelKey = vo.getChannelKey();
            //绑定好友和群，对应的channelKey
            channelKeyMap.put(vo.getRelationId(), channelKey);
            if (!chatMessageIdMap.containsKey(channelKey) || chatMessageIdMap.get(channelKey) < vo.getMessageId()) {
                //不存在当前会话，或者当前会话的curMaxMessageId<服务端里最小，那么拉取数据同步
                final Long curMaxMessageId = chatMessageIdMap.getOrDefault(channelKey, 0L);
                final ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder().channelKey(channelKey).messageIdStart(curMaxMessageId).messageIdEnd(vo.getMessageId()).build();
                final PageVO<ChatMessageVO> chatMessagePage = jiChatServerManager.queryChatMessage(chatMessageDTO, new PageDTO());
                log.info("将查询到的历史消息，同步到对话列表里{}条数", chatMessagePage.getTotal());
                chatMessageIdMap.put(channelKey, vo.getMessageId());
            }
        }
        log.info("channelKeyMap:{}", channelKeyMap);
        log.info("chatMessageIdMap:{}", chatMessageIdMap);
    }

    /**
     * 启动客户端
     */
    public void startClient() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(clientChannelInitializer)
        ;
        ChannelFuture future = null;
        try {
            final UserChatServerVO chatServerVO = clientInfo.getUserChatServerVO();
            future = bootstrap.connect(chatServerVO.getOutsideIp(), chatServerVO.getTcpPort()).sync();
        } catch (Exception e) {
            errorCount++;
            if (errorCount >= 3) {
                log.error("连接失败次数达到上限[{}]次", errorCount);
            }
            log.error("Connect fail!", e);
        }
        if (Objects.nonNull(future) && future.isSuccess()) {
            log.info("启动 JiChat Client 成功");
            channel = (SocketChannel) future.channel();
        } else {
            log.error("启动 JiChat Client 失败");
        }
    }


    /**
     * 向服务器注册
     */
    public void loginTcpServer() {
        final AppUpMessage appUpMessage = new AppUpMessage(clientInfo);
        appUpMessage.setCode(CommandCodeEnum.LOGIN.getCode());
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", clientInfo.getAuthLoginVO().getAccessToken());
        appUpMessage.setContent(jsonObject.toString());
        ChannelFuture future = channel.writeAndFlush(appUpMessage);
        future.addListener((ChannelFutureListener) channelFuture ->
                log.info("Registry JiChat server success!")
        );
    }

    /**
     * 发送私聊消息
     *
     * @param msg    消息内容
     * @param userId 发送到用户id
     * @author jisl on 2024/1/29 10:34
     **/
    public void privateMessage(String msg, long userId) {
        if (!channelKeyMap.containsKey(userId)) {
            throw new ServiceException("和他还不是好友:" + userId);
        }
        final AppUpMessage appUpMessage = new AppUpMessage(clientInfo);
        final ChatMessageSendDTO chatMessageDTO = ChatMessageSendDTO.builder()
                .messageFrom(clientInfo.getUserId()).messageTo(userId).messageType(ChatMessageTypeEnum.TEXT.getCode())
                .messageContent(msg).channelKey(channelKeyMap.get(userId))
                .build();
        appUpMessage.setCode(CommandCodeEnum.PRIVATE_MESSAGE.getCode());
        appUpMessage.setContent(JSON.toJSONString(chatMessageDTO));
        messagesQueue.add(appUpMessage);
        CompletableFuture.runAsync(this::syncSendMsg);
    }

    /**
     * 通过队列统一发送，等收到服务端messageId。再发送下一条消息。这样保证发送的消息顺序和服务端收到的顺序是一致
     *
     * @author jisl on 2024/1/24 16:30
     **/
    private void syncSendMsg() {
        while (!messagesQueue.isEmpty()) {
            final UpMessage upMessage = messagesQueue.pop();
            ChannelFuture future = channel.writeAndFlush(upMessage);
            future.addListener((ChannelFutureListener) channelFuture -> log.debug("客户端手动发消息成功={}", upMessage.getContent()));
            try {
                final GuardedObject<DownMessage> go = GuardedObject.create(upMessage.getNonce());
                final DownMessage downMessage = go.getAndThrow(t -> Objects.equals(t.getNonce(), upMessage.getNonce()), 3);
                final ChatMessageSendDTO messageSendDTO = JSON.parseObject(upMessage.getContent(), ChatMessageSendDTO.class);
                JSONObject jsonObject = JSON.parseObject(downMessage.getContent());
                chatMessageIdMap.put(messageSendDTO.getChannelKey(), jsonObject.getLongValue("messageId"));
                log.info("发送的消息收到messageId:{},{}", downMessage.getContent(), upMessage.getContent());
            } catch (InterruptedException e) {
                e.printStackTrace();
//                这边发送失败，可以将消息添加到队首。再进行重试。重试3次失败后。提示网络啥
            }
        }

    }

//    /**
//     * 发送 Google Protocol 编解码字符串
//     *
//     * @param googleProtocolVO
//     */
//    public void sendGoogleProtocolMsg(GoogleProtocolVO googleProtocolVO) {
//
//        CIMRequestProto.CIMReqProtocol protocol = CIMRequestProto.CIMReqProtocol.newBuilder()
//                .setRequestId(googleProtocolVO.getRequestId())
//                .setReqMsg(googleProtocolVO.getMsg())
//                .setType(Constants.CommandType.MSG)
//                .build();
//
//
//        ChannelFuture future = channel.writeAndFlush(protocol);
//        future.addListener((ChannelFutureListener) channelFuture ->
//                LOGGER.info("客户端手动发送 Google Protocol 成功={}", googleProtocolVO.toString()));
//
//    }


    /**
     * 1. clear route information.
     * 2. reconnect.
     * 3. shutdown reconnect job.
     * 4. reset reconnect state.
     */
    public void reconnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        //首先清除路由信息，下线
        jiChatServerManager.offLine();
        log.info("JiChat server shutdown, reconnecting....");
        //启动客户端
        startClient();
        //向服务端注册
        loginTcpServer();
        log.info("Great! reConnect success!!!");
    }

    /**
     * 关闭
     */
    public void close() {
        jiChatServerManager.offLine();
        if (channel != null) {
            channel.close();
        }
    }


    @Override
    public void run(String... args) {
        start();
    }
}
