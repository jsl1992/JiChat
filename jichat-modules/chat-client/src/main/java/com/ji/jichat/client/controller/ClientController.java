package com.ji.jichat.client.controller;


import com.ji.jichat.client.client.JiChatClient;
import com.ji.jichat.client.manager.JiChatServerManager;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"消息Controller "})
@RequestMapping("/client")
public class ClientController {

    @Resource
    private JiChatClient jiChatClient;

    @Resource
    private JiChatServerManager jiChatServerManager;


    @GetMapping("/loginTcpServer")
    @ApiOperation("loginTcpServer")
    public CommonResult<Void> loginTcpServer() {
        jiChatClient.loginTcpServer();
        return CommonResult.success();
    }

    @GetMapping("/reconnect")
    @ApiOperation("reconnect")
    public CommonResult<Void> reconnect() {
        jiChatClient.reconnect();
        return CommonResult.success();
    }

    @GetMapping("/privateMessage")
    @ApiOperation("One-on-One Chat / Private Chat: 单聊")
    public CommonResult<Void> privateMessage(String msg,  Integer messageType, long userId) {
        jiChatClient.privateMessage(msg,messageType, userId);
        return CommonResult.success();
    }



    @GetMapping("/openE2EE")
    @ApiOperation("开启端到端加密(只支持手机端之间)")
    public CommonResult<Void> openE2EE(long userId) {
        jiChatClient.openE2EE(userId);
        return CommonResult.success();
    }

    @GetMapping("/closeE2EE")
    @ApiOperation("关闭端到端加密")
    public CommonResult<Void> closeE2EE(long userId) {
        jiChatClient.closeE2EE(userId);
        return CommonResult.success();
    }


    @GetMapping("/close")
    @ApiOperation("close")
    public CommonResult<Void> close() {
        jiChatClient.close();
        return CommonResult.success();
    }


    @GetMapping("/queryChatMessage")
    @ApiOperation("queryChatMessage")
    public CommonResult<PageVO<ChatMessageVO>> queryChatMessage(ChatMessageDTO chatMessageDTO, PageDTO pageDTO) {
        return CommonResult.success(jiChatServerManager.queryChatMessage(chatMessageDTO, pageDTO));
    }

    @GetMapping("/syncHisMsg")
    @ApiOperation("syncHisMsg")
    public CommonResult<Void> syncHisMsg() {
        jiChatClient.syncHisMsg();
        return CommonResult.success();
    }


}
