package com.ji.jichat.client.manager;

import com.alibaba.fastjson.JSON;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author jisl on 2024/1/23 9:04
 */
@Component
@Slf4j
public class JiChatServerManager {

    @Value("${user.url}")
    private String userUrl;

    @Value("${chat.url}")
    private String chatUrl;


    @Autowired
    private ClientInfo clientInfo;


    /**
     * 登录+路由服务器
     *
     * @return 路由服务器信息
     * @throws Exception
     */
    public void userLogin() {
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
        log.info("请求返回值:{}",body.getData());
        return body.getData();

    }


    public void getUserChatServer() {
        final UserChatServerVO authLoginVO = exchangeResponseResult(chatUrl + "/chatServer/routeServer", HttpMethod.POST, null, new ParameterizedTypeReference<CommonResult<UserChatServerVO>>() {
        });
        clientInfo.setUserChatServerVO(authLoginVO);
    }

    public void offLine() {
        exchangeResponseResult(chatUrl + "/chatServer/offLine", HttpMethod.POST, null, new ParameterizedTypeReference<CommonResult<Void>>() {
        });
    }
}
