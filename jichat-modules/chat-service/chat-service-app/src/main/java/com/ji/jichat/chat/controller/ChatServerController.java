package com.ji.jichat.chat.controller;


import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.kit.ServerLoadBalancer;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    private UserChatServerCache userChatServerCache;


    @PostMapping("/routeServer")
    @ApiOperation("路由服务")
    public CommonResult<UserChatServerVO> routeServer() {
//        异步方法，防止定时器调用超时
        final String node = serverLoadBalancer.getServer();
        final LoginUser loginUser = UserContext.get();
        final String[] ipAndPort = node.split(":");
//       todo 需要将nacos的内网和http端口找到对应的服务，找到返回外网ip和tcp端口
        final UserChatServerVO userChatServerVO = UserChatServerVO.builder()
                .userId(loginUser.getUserId())
                .deviceType(loginUser.getDeviceType())
                .outsideIp("192.168.77.130").tcpPort(7066)
                .build();
        return CommonResult.success(userChatServerVO);
    }

    @PostMapping("/offLine")
    @ApiOperation("路由服务")
    public CommonResult<Void> offLine() {
        final LoginUser loginUser = UserContext.get();
        userChatServerCache.remove(loginUser.getUserId(), loginUser.getDeviceType());
        return CommonResult.success();
    }


}
