package com.ji.jichat.chat.controller;


import com.ji.jichat.chat.mq.producer.ChatMessageProducer;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.UserRpc;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"测试Controller "})
@RequestMapping("/test")
public class TestController {

    @Resource
    private ChatMessageProducer chatMessageProducer;

    @Resource
    private UserRpc userRpc;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    @ApiOperation("test")
    public CommonResult<String> test() {
        return CommonResult.success("成功");
    }


    @GetMapping("/sendMessage")
    @ApiOperation("sendMessage")
    public CommonResult<String> sendMessage(String message) {
        chatMessageProducer.sendMessage(message,"122222");
        return CommonResult.success("成功");
    }

    @GetMapping("/rpcTest")
    @ApiOperation("rpcTest")
    public CommonResult<LoginUser> rpcTest(String loginKey) {
        final CommonResult<LoginUser> loginUserCommonResult = userRpc.getLoginUserByLoginKey(loginKey);
        loginUserCommonResult.checkError();
        return CommonResult.success(loginUserCommonResult.getData());
    }


    @GetMapping("/getRedisCache")
    @ApiOperation("getRedisCache")
    public CommonResult<Object> getRedisCache(String key) {
        final Object o = redisTemplate.opsForValue().get(key);
        return CommonResult.success(o);
    }


}
