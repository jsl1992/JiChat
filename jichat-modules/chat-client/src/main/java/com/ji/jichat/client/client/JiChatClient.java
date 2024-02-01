package com.ji.jichat.client.client;


import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.ChatSendReturnMessage;
import com.ji.jichat.chat.api.dto.LoginMessage;
import com.ji.jichat.chat.api.enums.ChatMessageTypeEnum;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.client.dto.ChatChannelDTO;
import com.ji.jichat.client.manager.JiChatServerManager;
import com.ji.jichat.client.netty.ClientChannelInitializer;
import com.ji.jichat.client.utils.JiDigitUtil;
import com.ji.jichat.common.enums.CommonStatusEnum;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
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


    private static final ArrayDeque<ChatSendMessage> MESSAGES_QUEUE = new ArrayDeque<>();

    public static HashMap<String, Long> chatMessageIdMap = new HashMap<>();


    public static final HashMap<Long, ChatChannelDTO> chatChannelMap = new HashMap<>();


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
            chatChannelMap.put(vo.getRelationId(), ChatChannelDTO.builder().channelKey(channelKey).encryptType(0).build());
            if (!chatMessageIdMap.containsKey(channelKey) || chatMessageIdMap.get(channelKey) < vo.getMessageId()) {
                //不存在当前会话，或者当前会话的curMaxMessageId<服务端里最小，那么拉取数据同步
                final Long curMaxMessageId = chatMessageIdMap.getOrDefault(channelKey, 0L);
                final ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder().channelKey(channelKey).messageIdStart(curMaxMessageId).messageIdEnd(vo.getMessageId()).build();
                final PageVO<ChatMessageVO> chatMessagePage = jiChatServerManager.queryChatMessage(chatMessageDTO, new PageDTO());
                log.info("将查询到的历史消息，同步到对话列表里{}条数", chatMessagePage.getTotal());
                chatMessageIdMap.put(channelKey, vo.getMessageId());
            }
        }
        log.info("channelKeyMap:{}", chatChannelMap);
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
        LoginMessage loginMessage = new LoginMessage();
        clientInfo.fillMessage(loginMessage);
        loginMessage.setToken(clientInfo.getAuthLoginVO().getAccessToken());
        loginMessage.setCode(CommandCodeEnum.LOGIN.getCode());
        loginMessage.setIp(clientInfo.getIp());
        ChannelFuture future = channel.writeAndFlush(loginMessage);
        future.addListener((ChannelFutureListener) channelFuture ->
                log.info("Registry JiChat server success!")
        );
    }

    /**
     * 发送私聊消息
     *
     * @param msg         消息内容
     * @param messageType 消息类型 1：文字 2：图片 3：语音 4：视频 5：文件 6:RSA公钥 7:端到端密钥
     * @param userId      发送到用户id
     * @author jisl on 2024/1/29 10:34
     **/
    public void privateMessage(String msg, Integer messageType, long userId) {
        if (!chatChannelMap.containsKey(userId)) {
            throw new ServiceException("和他还不是好友:" + userId);
        }
        final ChatChannelDTO chatChannelDTO = chatChannelMap.get(userId);
        final ChatSendMessage chatMessage = ChatSendMessage.builder()
                .messageFrom(clientInfo.getUserId()).messageTo(userId).messageType(messageType)
                .messageContent(msg).channelKey(chatChannelDTO.getChannelKey()).encryptType(chatChannelDTO.getEncryptType())
                .build();
        clientInfo.fillMessage(chatMessage);
        if (Objects.equals(ChatMessageTypeEnum.TEXT.getCode(), messageType) && Objects.equals(chatChannelDTO.getEncryptType(), CommonStatusEnum.ENABLE.getStatus())) {
            //E2EE加密会话
            final JSONObject jsonObject = new JSONObject();
            final String nonce = JiDigitUtil.createNonce(16);
            jsonObject.put("ivStr", nonce);
            jsonObject.put("ciphertext", JiDigitUtil.encryptAes(msg, chatChannelDTO.getSecretKey(), nonce));
            chatMessage.setMessageContent(jsonObject.toJSONString());
        }
        chatMessage.setCode(CommandCodeEnum.PRIVATE_MESSAGE.getCode());
        MESSAGES_QUEUE.add(chatMessage);
        CompletableFuture.runAsync(this::syncSendMsg);
    }


    public void openE2EE(long userId) {
        final ChatChannelDTO chatChannelDTO = chatChannelMap.get(userId);
        final String[] keyPair = JiDigitUtil.genKeyPair(JiDigitUtil.RSA_ALGORITHM);
        String publicKeyBase64 = keyPair[0];
        String privateKeyBase64 = keyPair[1];
        chatChannelDTO.setPublicKey(publicKeyBase64);
        chatChannelDTO.setPrivateKey(privateKeyBase64);
        privateMessage(publicKeyBase64, ChatMessageTypeEnum.RSA_PUBLIC_KEY.getCode(), userId);
    }

    public void closeE2EE(long userId) {
        privateMessage("关闭", ChatMessageTypeEnum.IMAGE.getCode(), userId);
    }

    /**
     * 通过队列统一发送，等收到服务端messageId。再发送下一条消息。这样保证发送的消息顺序和服务端收到的顺序是一致
     *
     * @author jisl on 2024/1/24 16:30
     **/
    private void syncSendMsg() {
        while (!MESSAGES_QUEUE.isEmpty()) {
            final ChatSendMessage chatSendMessage = MESSAGES_QUEUE.pop();
            ChannelFuture future = channel.writeAndFlush(chatSendMessage);
            future.addListener((ChannelFutureListener) channelFuture -> log.debug("客户端手动发消息成功={}", chatSendMessage.getMessageContent()));
            try {
                final GuardedObject<ChatSendReturnMessage> go = GuardedObject.create(chatSendMessage.getNonce());
                final ChatSendReturnMessage returnMessage = go.getAndThrow(t -> Objects.equals(t.getNonce(), chatSendMessage.getNonce()));
                chatMessageIdMap.put(chatSendMessage.getChannelKey(), returnMessage.getMessageId());
                log.info("发送的消息收到messageId:{},{}", returnMessage.getMessageId(), returnMessage.getNonce());
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
