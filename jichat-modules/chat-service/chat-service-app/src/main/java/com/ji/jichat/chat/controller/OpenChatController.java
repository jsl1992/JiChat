package com.ji.jichat.chat.controller;


import com.ji.jichat.chat.kit.UserChatServerCache;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jisl on 2023/10/10 11:05
 **/
@RestController
@Tag(name = "开放OpenController ")
@RequestMapping("/open/chatServer/")
@Slf4j
public class OpenChatController {


    @Resource
    private UserChatServerCache userChatServerCache;


//    @PostMapping("/offLine")
//    @Operation(summary ="客户端下线")
//    public CommonResult<Void> offLine() {
//        final LoginUser loginUser = UserContext.get();
//        userChatServerCache.remove(loginUser.getUserId(), loginUser.getDeviceType());
//        return CommonResult.success();
//    }


}
