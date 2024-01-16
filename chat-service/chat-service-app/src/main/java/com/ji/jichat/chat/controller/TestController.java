package com.ji.jichat.chat.controller;


import com.alibaba.fastjson.JSONObject;

import framework.pojo.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/test")
    public CommonResult<Void> test() {
        return CommonResult.success();
    }

    public static void main(String[] args) {
        String aa = "12asfsf";
        String secretkey = "D829954D2335CD47A31C41A774B934DC";
        String iv = "43695068911831192233933895229435";
        try {
            String b = iv;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
