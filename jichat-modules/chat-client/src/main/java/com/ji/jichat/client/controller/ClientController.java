package com.ji.jichat.client.controller;


import com.ji.jichat.client.client.JiChatClient;
import com.ji.jichat.client.manager.JiChatServerManager;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import io.swagger.annotations.Api;
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
    public CommonResult<Void> privateMessage(String msg, long userId) {
        jiChatClient.privateMessage(msg,userId);
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
        final PageVO<ChatMessageVO> chatMessageVOPageVO = jiChatServerManager.queryChatMessage(chatMessageDTO, pageDTO);
        return CommonResult.success(chatMessageVOPageVO);
    }



}
