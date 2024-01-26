package com.ji.jichat.chat.controller;


import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.kit.ServerLoadBalancer;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.ChatServerInfoRpc;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author jisl on 2023/10/10 11:05
 **/
@RestController
@Api(tags = {"聊天服务信息Controller "})
@RequestMapping("/chatServer/")
@Slf4j
public class ChatServerController {

    @Resource
    private ServerLoadBalancer serverLoadBalancer;

    @Resource
    private ChatServerInfoRpc chatServerInfoRpc;


    @PostMapping("/routeServer")
    @ApiOperation("路由服务")
    public CommonResult<UserChatServerVO> routeServer() {
//        异步方法，防止定时器调用超时
        final String node = serverLoadBalancer.getServer();
        final LoginUser loginUser = UserContext.get();
        final String[] ipAndPort = node.split(":");
        final ChatServerInfoVO chatServerInfoVO = chatServerInfoRpc.getByIpAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])).getCheckedData();
        if (Objects.isNull(chatServerInfoVO)) {
            throw new ServiceException("暂时没有合适的服务器分配");
        }
        final UserChatServerVO userChatServerVO = UserChatServerVO.builder()
                .userId(loginUser.getUserId())
                .deviceType(loginUser.getDeviceType())
                .outsideIp(chatServerInfoVO.getOutsideIp()).tcpPort(chatServerInfoVO.getTcpPort())
                .build();
        return CommonResult.success(userChatServerVO);
    }

//    @PostMapping("/offLine")
//    @ApiOperation("客户端下线")
//    public CommonResult<Void> offLine() {
//        final LoginUser loginUser = UserContext.get();
//        final String userKey = userChatServerCache.getUserKey(loginUser.getUserId(), loginUser.getDeviceType());
//        userChatServerCache.remove(userKey);
//        return CommonResult.success();
//    }


}
