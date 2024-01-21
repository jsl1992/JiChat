package com.ji.jichat.user.controller;


import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.vo.UserChatServerVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.service.IUserService;
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
@Api(tags = {"发现服务Controller "})
@RequestMapping("/discoveryServer/")
@Slf4j
public class DiscoveryServerController {

    @Resource
    private IUserService userService;


    @PostMapping("/routeServer")
    @ApiOperation("路由服务")
    public CommonResult<UserChatServerVO> routeServer() {
//        异步方法，防止定时器调用超时
        final LoginUser loginUser = UserContext.get();
        return CommonResult.success(userService.routeServer(loginUser));
    }


}
