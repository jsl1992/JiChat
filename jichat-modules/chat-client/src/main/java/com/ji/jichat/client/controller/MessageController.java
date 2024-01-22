package com.ji.jichat.client.controller;


import com.ji.jichat.client.client.JiChatClient;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.UserRpc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"消息Controller "})
@RequestMapping("/message")
public class MessageController {

    @Resource
    private JiChatClient jiChatClient;


    @GetMapping("/loginTcpServer")
    @ApiOperation("loginTcpServer")
    public CommonResult<Void> loginTcpServer() {
        jiChatClient.loginTcpServer();
        return CommonResult.success();
    }


}
