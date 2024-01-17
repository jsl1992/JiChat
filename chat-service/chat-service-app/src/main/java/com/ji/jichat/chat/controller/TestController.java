package com.ji.jichat.chat.controller;


import com.ji.jichat.chat.mq.producer.MessageProducer;
import framework.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"测试Controller "})
@RequestMapping("/test")
public class TestController {

    @Resource
    private MessageProducer messageProducer;

    @GetMapping("/test")
    @ApiOperation("test")
    public CommonResult<String> test() {
        return CommonResult.success("成功");
    }


    @GetMapping("/sendMessage")
    @ApiOperation("sendMessage")
    public CommonResult<String> sendMessage(String message) {
        messageProducer.sendMessage(message);
        return CommonResult.success("成功");
    }


}
