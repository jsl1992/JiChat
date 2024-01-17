package com.ji.jichat.chat.controller;


import framework.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"测试Controller "})
@RequestMapping("/test")
public class TestController {


    @GetMapping("/test")
    @ApiOperation("test")
    public CommonResult<String> test() {
        return CommonResult.success("成功");
    }


}
