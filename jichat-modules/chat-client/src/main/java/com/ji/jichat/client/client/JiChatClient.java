package com.ji.jichat.client.client;


import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.client.dto.AppUpMessage;
import com.ji.jichat.client.netty.ClientChannelInitializer;
import com.ji.jichat.common.enums.CommandCodeEnum;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 22/05/2018 14:19
 * @since JDK 1.8
 */
@Component
@Slf4j
public class JiChatClient {


    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("jichat-work"));


    @Value("${user.url}")
    private String userUrl;

    @Value("${chat.url}")
    private String chatUrl;

    private SocketChannel channel;


    @Autowired
    private AppConfiguration configuration;


    @Autowired
    private ClientInfo clientInfo;

//    @Autowired
//    private ReConnectManager reConnectManager;

    /**
     * 重试次数
     */
    private int errorCount;

    @PostConstruct
    public void start() {
        //登录 + 获取可以使用的服务器 ip+port
        userLogin();
        //启动客户端
        startClient();
        //向服务端注册
        loginTcpServer();
    }

    /**
     * 启动客户端
     *
     * @throws Exception
     */
    private void startClient() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer())
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
     * 登录+路由服务器
     *
     * @return 路由服务器信息
     * @throws Exception
     */
    private void userLogin() {
        final AuthLoginDTO loginDTO = AuthLoginDTO.builder()
                .username(clientInfo.getUserName()).password(clientInfo.getPassword()).deviceIdentifier(clientInfo.getDeviceIdentifier())
                .deviceName(clientInfo.getDeviceName()).deviceType(clientInfo.getDeviceType()).osType(clientInfo.getOsType())
                .build();
        final String url = userUrl + "/user/login";
        final AuthLoginVO authLoginVO = exchangeResponseResult(url, HttpMethod.POST, loginDTO, new ParameterizedTypeReference<CommonResult<AuthLoginVO>>() {
        });
        clientInfo.setAuthLoginVO(authLoginVO);
        try {
            clientInfo.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        getUserChatServer();

    }


    public <T> T exchangeResponseResult(String url, HttpMethod httpMethod, Object request, ParameterizedTypeReference<CommonResult<T>> typeReference, Object... uriVariables) {
        log.info("{},url=[{}],uriVariables={},requestBody=[ {} ]", httpMethod.name(), url, Arrays.toString(uriVariables), JSON.toJSONString(request));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (Objects.nonNull(clientInfo.getAuthLoginVO())) {
            headers.set("Authorization", clientInfo.getAuthLoginVO().getAccessToken());
        }
        HttpEntity httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<CommonResult<T>> res = restTemplate.exchange(url,
                httpMethod,
                httpEntity,
                typeReference,
                uriVariables);
        final CommonResult<T> body = res.getBody();
        body.checkError();
        return body.getData();

    }


    private void getUserChatServer() {
        final UserChatServerVO authLoginVO = exchangeResponseResult(chatUrl + "/discoveryServer/routeServer", HttpMethod.POST, null, new ParameterizedTypeReference<CommonResult<UserChatServerVO>>() {
        });
        clientInfo.setUserChatServerVO(authLoginVO);
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
                log.info("Registry cim server success!")
        );
    }

    /**
     * 发送消息字符串
     *
     * @param msg
     */
    public void sendStringMsg(String msg) {
        final AppUpMessage appUpMessage = new AppUpMessage(clientInfo);
        ChannelFuture future = channel.writeAndFlush(appUpMessage);
        future.addListener((ChannelFutureListener) channelFuture ->
                log.info("客户端手动发消息成功={}", msg));

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
     *
     */
    public void reconnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        //首先清除路由信息，下线
//        routeRequest.offLine();
        log.info("cim server shutdown, reconnecting....");
        start();
        log.info("Great! reConnect success!!!");
//        reConnectManager.reConnectSuccess();
    }

    /**
     * 关闭
     *
     */
    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
